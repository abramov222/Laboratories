package Lab8;

public class SOLIDDemo {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа 8: SOLID (ISP + DIP) ===\n");

        // Демонстрация DIP (Dependency Inversion)
        System.out.println("1. Принцип DIP - Dependency Inversion:");
        System.out.println("-".repeat(40));

        // Создаем разные реализации BackendService
        BackendService javaBackend = new JavaBackend();
        BackendService pythonBackend = new PythonBackend();

        // Разработчики зависят от интерфейса, а не конкретной реализации
        Developer dev1 = new Developer("Ilya", javaBackend);
        Developer dev2 = new Developer("Alex", pythonBackend);

        dev1.work();
        dev1.code();
        dev1.useBackend();

        System.out.println();
        dev2.work();
        dev2.code();
        dev2.useBackend();

        System.out.println("\n2. Принцип ISP - Interface Segregation:");
        System.out.println("-".repeat(40));

        // Разные специалисты реализуют только нужные интерфейсы
        BackendOnlyDeveloper backendDev = new BackendOnlyDeveloper("Backend Max");
        FullStackDeveloper fullstackDev = new FullStackDeveloper("Fullstack Anna");

        backendDev.work();
        backendDev.code();
        // backendDev.designUI(); // ОШИБКА! У BackendOnlyDeveloper нет этого метода

        System.out.println();
        fullstackDev.work();
        fullstackDev.code();
        fullstackDev.designUI(); // FullStackDeveloper имеет все методы

        System.out.println("\n3. Использование в проекте (ISP + DIP вместе):");
        System.out.println("-".repeat(40));

        // Проект зависит от интерфейса BackendService (DIP)
        Project javaProject = new Project(javaBackend);
        Project pythonProject = new Project(pythonBackend);

        // Добавляем команду с разными специалистами (ISP)
        javaProject.addTeamMember(dev1);
        javaProject.addTeamMember(backendDev);
        javaProject.addTeamMember(fullstackDev);

        pythonProject.addTeamMember(dev2);
        pythonProject.addTeamMember(fullstackDev);

        System.out.println("Java проект:");
        javaProject.startProject();

        System.out.println("\nPython проект:");
        pythonProject.startProject();

        System.out.println("\n=== Демонстрация завершена ===");
    }
}