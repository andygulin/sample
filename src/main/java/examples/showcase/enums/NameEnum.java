package examples.showcase.enums;

public enum NameEnum {
	AA {
		@Override
		public String getName() {
			return "My Name is AA...";
		}
	},
	BB {
		@Override
		public String getName() {
			return "My Name is BB...";
		}
	},
	CC {
		@Override
		public String getName() {
			return "My Name is CC...";
		}
	};

	public abstract String getName();
}