package examples.showcase.singleton;

public class SingletonClass {

	private SingletonClass() {
	}

	private static SingletonClass INSTANCE;

	static {
		INSTANCE = new SingletonClass();
	}

	public static SingletonClass getInstance() {
		return INSTANCE;
	}
}
