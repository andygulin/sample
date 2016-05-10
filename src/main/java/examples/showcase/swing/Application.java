package examples.showcase.swing;

public class Application {
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new LoginFrame();
			}
		}).start();
		;
	}
}