package examples.showcase.swing;

public class Application {
    public static void main(String[] args) {
        new Thread(() -> new LoginFrame()).start();
    }
}