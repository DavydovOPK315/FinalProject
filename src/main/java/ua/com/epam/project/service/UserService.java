package ua.com.epam.project.service;

import ua.com.epam.project.entity.User;

public interface UserService {

    boolean registerUser(User user);

    User getUserByLoginAndPassword(String login, String pass);
}