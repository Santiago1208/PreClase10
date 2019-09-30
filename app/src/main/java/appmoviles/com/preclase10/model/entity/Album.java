package appmoviles.com.preclase10.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Album implements Serializable {

    private String id;
    private String name;
    private Date date;

    public Album() {
    }

    public Album(String id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
