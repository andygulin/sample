package examples.showcase.builder;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person {

	private final String firstName;
	private final String lastName;
	private final int gender;
	private final int age;
	private final Date birthday;
	private final String address;
	private final String city;

	private Person(Builder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.gender = builder.gender;
		this.age = builder.age;
		this.birthday = builder.birthday;
		this.address = builder.address;
		this.city = builder.city;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public static class Builder {
		private String firstName;
		private String lastName;
		private int gender;
		private int age;
		private Date birthday;
		private String address;
		private String city;

		public Person build() {
			return new Person(this);
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder setGender(int gender) {
			this.gender = gender;
			return this;
		}

		public Builder setAge(int age) {
			this.age = age;
			return this;
		}

		public Builder setBirthday(Date birthday) {
			this.birthday = birthday;
			return this;
		}

		public Builder setAddress(String address) {
			this.address = address;
			return this;
		}

		public Builder setCity(String city) {
			this.city = city;
			return this;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}