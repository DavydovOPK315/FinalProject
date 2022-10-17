package ua.com.epam.project.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User DTO
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class UserDto {
    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private Date created;
    private String status;
    private String role;
    private List<Performance> performanceList;
    private int averageGrade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Performance> getPerformanceList() {
        if (performanceList == null)
            performanceList = new ArrayList<>();
        return performanceList;
    }

    public void setPerformanceList(List<Performance> performanceList) {
        this.performanceList = performanceList;
    }

    public int getAverageGrade() {
        if (performanceList == null) return 0;
        return (int) performanceList.stream().mapToInt(Performance::getGrade).average().orElse(0);
    }
}