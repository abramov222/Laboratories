package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;
    private String nickname;
    private volatile boolean running = true;

    public ChatClient(String host, int port) {
        scanner = new Scanner(System.in);
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            startMessageReader();
            authenticate();
            startMessageWriter();
        } catch (IOException e) {
            logger.error("Ошибка подключения к серверу", e);
        }
    }

    private void authenticate() throws IOException {
        System.out.print(reader.readLine() + " "); // "Введите ваш никнейм:"
        nickname = scanner.nextLine();
        writer.println(nickname);

        String response = reader.readLine();
        if (response != null && response.startsWith("ERROR")) {
            System.out.println(response);
            System.exit(1);
        }
    }

    private void startMessageReader() {
        Thread readThread = new Thread(() -> {
            try {
                String message;
                while (running && (message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (running) {
                    logger.info("Соединение с сервером разорвано");
                }
            } finally {
                running = false;
            }
        });
        readThread.setDaemon(true);
        readThread.start();
    }

    private void startMessageWriter() {
        try {
            // Читаем приветственное сообщение от сервера
            String welcome = reader.readLine();
            if (welcome != null) {
                System.out.println(welcome);
            }

            // Читаем список команд от сервера
            String commands = reader.readLine();
            if (commands != null) {
                System.out.println(commands);
            }

            // информация о командах
            System.out.println("Дополнительные команды:");
            System.out.println("  /help - показать команды");
            System.out.println("  /exit - выйти из чата");

            while (running) {
                System.out.print("> ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("/exit")) {
                    writer.println("/exit");
                    break;
                } else if (input.equalsIgnoreCase("/help")) {
                    showHelp();
                } else if (!input.trim().isEmpty()) {
                    writer.println(input);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при чтении от сервера", e);
        } finally {
            close();
        }
    }

    private void showHelp() {
        System.out.println("=== Доступные команды ===");
        System.out.println("/users - показать список пользователей онлайн");
        System.out.println("/private - отправить личное сообщение");
        System.out.println("/help - показать эту справку");
        System.out.println("/exit - выйти из чата");
        System.out.println("Любой другой текст будет отправлен всем пользователям");
        System.out.println("=======================");
    }

    private void close() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сокета", e);
        }
        scanner.close();
        System.out.println("Клиент завершил работу");
    }

    public static void main(String[] args) {
        ConfigReader config = new ConfigReader();
        String host = config.getProperty("client.server.host");
        int port = config.getIntProperty("client.server.port");

        new ChatClient(host, port);
    }
}