package examples.showcase.singleton;

public enum SingletonEnum {
	INSTANCE;

	private SingletonEnum() {
	}

	public SingletonEnum getInstance() {
		return SingletonEnum.INSTANCE;
	}
}