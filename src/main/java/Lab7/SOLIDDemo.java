package Lab7;

import java.util.ArrayList;
import java.util.List;

public class SOLIDDemo {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа 7: SOLID ===\n");

        // Создаем пользователя
        User user = new User("Ilya", "abramov222@.com");

        // 1. ДЕМОНСТРАЦИЯ SRP
        System.out.println("1. Принцип SRP (Single Responsibility):");
        System.out.println("-".repeat(40));

        UserValidator validator = new UserValidator();
        UserRepository repository = new UserRepository();
        EmailService emailService = new EmailService();
        Logger logger = new Logger();

        if (validator.validate(user)) {
            repository.save(user);
            emailService.sendWelcomeEmail(user);
            logger.logActivity(user, "registered");
        }
        System.out.println();

        // 2. ДЕМОНСТРАЦИЯ OCP
        System.out.println("2. Принцип OCP (Open/Closed):");
        System.out.println("-".repeat(40));

        List<Report> reports = new ArrayList<>();
        reports.add(new PdfReport());
        reports.add(new ExcelReport());
        reports.add(new HtmlReport()); // Новый тип без изменения кода

        for (Report report : reports) {
            System.out.print(report.getType() + " report: ");
            report.generate();
        }
        System.out.println();

        // 3. ДЕМОНСТРАЦИЯ LSP
        System.out.println("3. Принцип LSP (Liskov Substitution):");
        System.out.println("-".repeat(40));

        List<Animal> animals = new ArrayList<>();
        animals.add(new Bird("Eagle"));
        animals.add(new Penguin("Penguin"));
        animals.add(new Fish("Goldfish")); // Новый тип

        // Все животные взаимозаменяемы
        for (Animal animal : animals) {
            System.out.print(animal.getName() + ": ");
            animal.move(); // Каждое животное двигается по-своему
        }

        // Демонстрация специфичных методов
        System.out.println("\nСпецифичные методы:");
        Bird bird = new Bird("Sparrow");
        bird.fly(); // Только птицы могут летать

        Penguin penguin = new Penguin("Penguin");
        penguin.swim(); // Пингвины хорошо плавают

        System.out.println("\n=== Завершение демонстрации ===");
    }
}