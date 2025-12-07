package Lab6;

public class MyClass {
    public void publicMethod1() {
        System.out.println("Публичный метод 1");
    }

    public String publicMethod2(String name) {
        return "Привет от " + name;
    }

    @Repeat(times = 2)
    protected void protectedMethod1() {
        System.out.println("Защищенный метод 1");
    }

    @Repeat(times = 1)
    protected int protectedMethod2(int a, int b) {
        System.out.println("Защищенный метод 2: " + a + "+" + b);
        return a + b;
    }

    @Repeat(times = 3)
    private void privateMethod1() {
        System.out.println("Приватный метод 1");
    }

    @Repeat(times = 2)
    private String privateMethod2(String text, int count) {
        String result = text.repeat(count);
        System.out.println("Приватный метод 2: " + result);
        return result;
    }

    private void privateMethodWithoutAnnotation() {
        System.out.println("Метод без аннотации");
    }
}