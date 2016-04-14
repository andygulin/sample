package examples.showcase.enums;

public enum NameEnum {
	AA {
		@Override
		public void print() {
			System.out.println("My Name is AA...");
		}
	},
	BB {
		@Override
		public void print() {
			System.out.println("My Name is BB...");
		}
	},
	CC {
		@Override
		public void print() {
			System.out.println("My Name is CC...");
		}
	};

	public abstract void print();
}