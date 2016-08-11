package examples.showcase;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User implements Comparable<User>, Cloneable, Serializable {

	private static final long serialVersionUID = 8276648076669784264L;

	private Integer id;
	private String name;
	private Integer age;
	private String address;
	private Date createAt;

	public User() {
	}

	public User(Integer id, String name, Integer age, String address, Date createAt) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.createAt = createAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public int compareTo(User u) {
		if (id > u.getId())
			return 1;
		if (id < u.getId())
			return -1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		return u.getId().intValue() == this.getId().intValue();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}