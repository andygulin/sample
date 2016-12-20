package examples.showcase.singleton;

public class SingletonClass {

    private static SingletonClass INSTANCE;

    static {
        INSTANCE = new SingletonClass();
    }

    private SingletonClass() {
    }

    public static SingletonClass getInstance() {
        return INSTANCE;
    }
}
