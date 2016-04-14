package examples.showcase.json;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Custom implements Serializable {

	private static final long serialVersionUID = 6990848215792571019L;

	private long id;
	private String name;
	private List<Order> orders;

	public Custom() {
	}

	public Custom(long id, String name, List<Order> orders) {
		this.id = id;
		this.name = name;
		this.orders = orders;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
