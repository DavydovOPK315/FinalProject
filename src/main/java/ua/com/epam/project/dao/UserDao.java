package ua.com.epam.project.dao;

import ua.com.epam.project.entity.User;

public interface UserDao {
    boolean saveUser(User user);

    User getUserByLoginAndPassword(String login, String pass);
}
