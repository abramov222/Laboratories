package Lab6;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА 6 ===\n");

        System.out.println("--- Часть 1: Рефлексия ---");
        MyClass obj = new MyClass();
        Invoker.invokeAnnotatedMethods(obj);

        System.out.println("--- Часть 2: Файловая система ---");
        FileSystemOperations.performFileOperations();

        System.out.println("\n=== РАБОТА ЗАВЕРШЕНА ===");
    }
}