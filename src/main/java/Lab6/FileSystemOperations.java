package Lab6;

import java.nio.file.*;

public class FileSystemOperations {
    public static void performFileOperations() {
        String surname = "Abramov";
        String name = "Ilya";

        try {
            // 1. Создание директории
            Path mainDir = Paths.get(surname);
            Files.createDirectories(mainDir);
            System.out.println("1. Создана директория: " + surname);

            // 2. Создание файла
            Path nameFile = mainDir.resolve(name + ".txt");
            Files.createFile(nameFile);
            Files.writeString(nameFile, "Файл " + name);
            System.out.println("2. Создан файл: " + name + ".txt");

            // 3. Создание вложенных директорий
            Path nestedDir = mainDir.resolve("dir1/dir2/dir3");
            Files.createDirectories(nestedDir);
            System.out.println("3. Созданы dir1/dir2/dir3");

            // 4. Копирование файла
            Path copiedFile = nestedDir.resolve(name + ".txt");
            Files.copy(nameFile, copiedFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("4. Файл скопирован в dir1/dir2/dir3/");

            // 5. Создание file1
            Path file1 = mainDir.resolve("dir1/file1.txt");
            Files.createFile(file1);
            Files.writeString(file1, "file1 content");
            System.out.println("5. Создан file1.txt");

            // 6. Создание file2
            Path file2 = mainDir.resolve("dir1/dir2/file2.txt");
            Files.createFile(file2);
            Files.writeString(file2, "file2 content");
            System.out.println("6. Создан file2.txt");

            // 7. Рекурсивный обход
            System.out.println("\n7. Содержимое " + surname + ":");
            Files.walk(mainDir)
                    .sorted()
                    .forEach(path -> {
                        if (Files.isDirectory(path)) {
                            System.out.println("D: " + mainDir.relativize(path));
                        } else {
                            System.out.println("F: " + mainDir.relativize(path));
                        }
                    });

            // 8. Удаление dir1
            System.out.println("\n8. Удаление dir1...");
            Path dir1 = mainDir.resolve("dir1");
            Files.walk(dir1)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (Exception e) {}
                    });

            // 9. Финальный вид
            System.out.println("9. Финальная структура:");
            Files.list(mainDir)
                    .forEach(path -> {
                        if (Files.isDirectory(path)) {
                            System.out.println("D: " + path.getFileName());
                        } else {
                            System.out.println("F: " + path.getFileName());
                        }
                    });

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}