package ua.com.epam.project.entity;

import java.util.Date;
import java.util.Objects;

/**
 * Course class
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class Course extends BaseEntity {
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return this.getId() == course.getId() && Objects.equals(getName(), course.getName()) && Objects.equals(getDateStart(), course.getDateStart()) && Objects.equals(getDateEnd(), course.getDateEnd()) && Objects.equals(getDescription(), course.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateStart(), getDateEnd(), getDescription());
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", description='" + description + '\'' +
                '}';
    }
}