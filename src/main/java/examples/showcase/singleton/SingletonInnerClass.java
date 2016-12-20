package examples.showcase.singleton;

public class SingletonInnerClass {

    private SingletonInnerClass() {
    }

    public static SingletonInnerClass getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
    }
}
