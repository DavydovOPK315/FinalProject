package ua.com.epam.project.entity;

import java.util.Objects;

/**
 * Topic class
 *
 * @author Denis Davydov
 * @version 2.0
 * @see BaseEntity
 */
public class Topic extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
        return getId() == topic.getId() && getName().equals(topic.getName()) && getCreated().equals(topic.getCreated()) && getStatus().equals(topic.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreated(), getStatus());
    }

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                "created='" + getCreated() + '\'' +
                "status='" + getStatus() + '\'' +
                '}';
    }
}