package examples.showcase.singleton;

public class SingletonInnerClass {

	private SingletonInnerClass() {
	}

	private static final class SingletonHolder {
		private static final SingletonInnerClass INSTANCE = new SingletonInnerClass();
	}

	public static SingletonInnerClass getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
