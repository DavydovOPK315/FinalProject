package ua.com.epam.project.entity;

import java.util.Objects;

/**
 * Role class
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class Role extends BaseEntity {
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
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId() == role.getId() && getName().equals(role.getName()) && getCreated().equals(role.getCreated()) && getStatus().equals(role.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreated(), getStatus());
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                "created='" + getCreated() + '\'' +
                '}';
    }
}