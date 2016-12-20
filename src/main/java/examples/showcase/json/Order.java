package examples.showcase.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Order implements Serializable {

    private static final long serialVersionUID = 7699911200778729796L;

    private long id;
    private String name;

    public Order() {
    }

    public Order(long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
