package ua.com.epam.project.service.impl;

import ua.com.epam.project.dao.Impl.UserDaoImpl;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = UserDaoImpl.getInstance();
    private static UserService instance;

    private UserServiceImpl(){}

    public static synchronized UserService getInstance(){
        if (instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean registerUser(User user) {
        System.out.println("UserService.registerUser");
        userDao.saveUser(user);
        return true;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String pass){
        System.out.println("UserService.getUserByLoginAndPassword");
        return userDao.getUserByLoginAndPassword(login, pass);
    }
}