package ua.com.epam.project.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Base class
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class BaseEntity implements Serializable {
    private int id;
    private Date created;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}