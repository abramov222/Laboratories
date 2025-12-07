package Lab6;
import java.lang.reflect.Method;

public class Invoker {
    public static void invokeAnnotatedMethods(MyClass obj) {
        try {
            System.out.println("=== Invoker запущен ===");
            Method[] methods = obj.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Repeat.class)) {
                    Repeat annotation = method.getAnnotation(Repeat.class);
                    int times = annotation.times();

                    System.out.println("Метод: " + method.getName() + " (вызовов: " + times + ")");

                    method.setAccessible(true);

                    for (int i = 0; i < times; i++) {
                        System.out.print("Вызов " + (i+1) + ": ");

                        if (method.getParameterCount() == 0) {
                            method.invoke(obj);
                        } else if (method.getName().equals("protectedMethod2")) {
                            Object result = method.invoke(obj, 10, 5);
                            System.out.println("Результат: " + result);
                        } else if (method.getName().equals("privateMethod2")) {
                            Object result = method.invoke(obj, "Test ", 3);
                            System.out.println("Результат: " + result);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println("=== Invoker завершен ===\n");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}