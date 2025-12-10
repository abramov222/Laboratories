package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static final Logger chatLogger = LoggerFactory.getLogger("chat");

    private int port;
    private ServerSocket serverSocket;
    private boolean isRunning = true;
    private ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Сервер запущен на порту {}", port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(() -> handleNewClient(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Ошибка при работе сервера", e);
        }
    }

    private void handleNewClient(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // Получаем никнейм
            writer.println("Введите ваш никнейм:");
            String nickname = reader.readLine();

            if (nickname == null || nickname.trim().isEmpty()) {
                writer.println("ERROR: Никнейм не может быть пустым");
                clientSocket.close();
                return;
            }

            synchronized (clients) {
                if (clients.containsKey(nickname)) {
                    writer.println("ERROR: Никнейм уже занят");
                    clientSocket.close();
                    return;
                }

                ClientHandler handler = new ClientHandler(
                        clientSocket, nickname, reader, writer, this);
                clients.put(nickname, handler);
                threadPool.execute(handler);

                logger.info("Пользователь '{}' подключился", nickname);
                broadcastSystemMessage(nickname + " присоединился к чату", null);
                sendUserList();
            }
        } catch (IOException e) {
            logger.error("Ошибка при обработке нового клиента", e);
        }
    }

    public void broadcastMessage(String message, String sender, String recipient) {
        if (recipient.equals("ALL")) {
            // Широковещательное сообщение
            for (ClientHandler client : clients.values()) {
                if (!client.getNickname().equals(sender)) {
                    client.sendMessage("[" + sender + " -> ALL]: " + message);
                }
            }
            chatLogger.info("[{} -> ALL]: {}", sender, message);
        } else {
            // Личное сообщение
            ClientHandler recipientClient = clients.get(recipient);
            if (recipientClient != null) {
                recipientClient.sendMessage("[" + sender + " -> " + recipient + "]: " + message);
                chatLogger.info("[{} -> {}]: {}", sender, recipient, message);
            } else {
                ClientHandler senderClient = clients.get(sender);
                if (senderClient != null) {
                    senderClient.sendMessage("ERROR: Пользователь '" + recipient + "' не найден");
                }
            }
        }
    }

    public void broadcastSystemMessage(String message, String excludeUser) {
        for (ClientHandler client : clients.values()) {
            if (excludeUser == null || !client.getNickname().equals(excludeUser)) {
                client.sendMessage("[СИСТЕМА]: " + message);
            }
        }
    }

    public void sendUserList() {
        StringBuilder userList = new StringBuilder("Список пользователей: ");
        for (String nickname : clients.keySet()) {
            userList.append(nickname).append(", ");
        }
        if (userList.length() > 0) {
            userList.setLength(userList.length() - 2);
        }

        for (ClientHandler client : clients.values()) {
            client.sendMessage(userList.toString());
        }
    }

    public void removeClient(String nickname) {
        clients.remove(nickname);
        logger.info("Пользователь '{}' отключился", nickname);
        broadcastSystemMessage(nickname + " покинул чат", null);
        sendUserList();
    }

    public List<String> getOnlineUsers(String excludeUser) {
        List<String> users = new ArrayList<>();
        for (String user : clients.keySet()) {
            if (!user.equals(excludeUser)) {
                users.add(user);
            }
        }
        return users;
    }

    public void shutdown() {
        isRunning = false;
        threadPool.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сервера", e);
        }
    }

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader();
        int port = config.getIntProperty("server.port");

        ChatServer server = new ChatServer(port);
        server.start();
    }

    class ClientHandler implements Runnable {
        private Socket socket;
        private String nickname;
        private BufferedReader reader;
        private PrintWriter writer;
        private ChatServer server;

        public ClientHandler(Socket socket, String nickname,
                             BufferedReader reader, PrintWriter writer,
                             ChatServer server) {
            this.socket = socket;
            this.nickname = nickname;
            this.reader = reader;
            this.writer = writer;
            this.server = server;
        }

        public String getNickname() {
            return nickname;
        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        @Override
        public void run() {
            try {
                writer.println("Добро пожаловать в чат, " + nickname + "!");
                writer.println("Доступные команды:");
                writer.println("  /users - показать список пользователей");
                writer.println("  /private - отправить личное сообщение");
                writer.println("  /exit - выйти из чата");
                writer.println("Для отправки сообщения всем просто введите текст");

                String input;
                while ((input = reader.readLine()) != null) {
                    if (input.equalsIgnoreCase("/exit")) {
                        break;
                    } else if (input.equalsIgnoreCase("/users")) {
                        List<String> users = server.getOnlineUsers(nickname);
                        writer.println("Онлайн пользователи: " + String.join(", ", users));
                    } else if (input.equalsIgnoreCase("/private")) {
                        handlePrivateMessage();
                    } else {
                        // Широковещательное сообщение
                        server.broadcastMessage(input, nickname, "ALL");
                    }
                }
            } catch (IOException e) {
                logger.error("Ошибка при работе с клиентом {}", nickname, e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("Ошибка при закрытии сокета", e);
                }
                server.removeClient(nickname);
            }
        }

        private void handlePrivateMessage() throws IOException {
            // Получаем список пользователей
            List<String> users = server.getOnlineUsers(nickname);
            if (users.isEmpty()) {
                writer.println("Нет других пользователей в чате");
                return;
            }

            writer.println("Выберите получателя из списка: " + String.join(", ", users));
            writer.println("Введите никнейм получателя:");
            String recipient = reader.readLine();

            if (recipient == null || recipient.trim().isEmpty()) {
                writer.println("Ошибка: не указан получатель");
                return;
            }

            if (!users.contains(recipient)) {
                writer.println("Ошибка: пользователь '" + recipient + "' не найден");
                return;
            }

            writer.println("Введите сообщение для " + recipient + ":");
            String message = reader.readLine();

            if (message != null && !message.trim().isEmpty()) {
                server.broadcastMessage(message, nickname, recipient);
                writer.println("Сообщение отправлено пользователю " + recipient);
            }
        }
    }
}