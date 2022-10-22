package ua.com.epam.project.service.impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.DAOFactory;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;

import java.util.List;

/**
 * User service implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = DAOFactory.getUserDao();
    private static UserService instance;
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private UserServiceImpl() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null)
            instance = new UserServiceImpl();
        return instance;
    }

    @Override
    public boolean registerUser(User user) {
        boolean result = userDao.saveUser(user);
        if (result)
            LOG.info("User has been registered with login: " + user.getLogin());
        else
            LOG.warn("User has not been registered with login: " + user.getLogin());
        return result;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String pass) {
        User user = userDao.getUserByLoginAndPassword(login, pass);
        if (user == null)
            LOG.warn("User was not found with login: " + login);
        else
            LOG.info("User was found with login: " + login);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        if (user == null)
            LOG.warn("User was not found with email: " + email);
        else
            LOG.info("User was found with email: " + email);
        return user;
    }

    @Override
    public boolean updateResetPasswordToken(String token, int userId) {
        boolean result = userDao.updateResetPasswordToken(token, userId);
        if (result)
            LOG.info("Updated ResetPasswordToken");
        else
            LOG.warn("Unable to update ResetPasswordToken");
        return result;
    }

    @Override
    public User getUserByToken(String token) {
        User user = userDao.getUserByToken(token);
        if (user == null)
            LOG.warn("User was not found with token: " + token);
        else
            LOG.info("User was found by token with login: " + user.getLogin());
        return user;
    }

    @Override
    public boolean updatePassword(User user, String password) {
        boolean result = userDao.updatePassword(user, password);
        if (result)
            LOG.info("Password was updated for user with login: " + user.getLogin());
        else
            LOG.warn("Unable to update password for user with login: " + user.getLogin());
        return result;
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = userDao.getAll();
        LOG.info(result.size() + " users were found");
        return result;
    }

    @Override
    public List<UserDto> getAllByStatus(String status) {
        List<UserDto> result = userDao.getAllByStatus(status);
        LOG.info(result.size() + " users were found by status: " + status);
        return result;
    }

    @Override
    public List<UserDto> getAllByRole(String role) {
        List<UserDto> result = userDao.getAllByRole(role);
        LOG.info(result.size() + " users were found by role: " + role);
        return result;
    }

    @Override
    public UserDto getUserByLogin(String login) {
        UserDto result = userDao.getUserByLogin(login);
        if (result == null)
            LOG.warn("User was not found with login: " + login);
        else
            LOG.info("User was found by login: " + login);
        return result;
    }

    @Override
    public UserDto getUserById(int userId) {
        UserDto userDto = userDao.getUserById(userId);
        if (userDto == null)
            LOG.warn("User was not found with id: " + userId);
        else
            LOG.info("User was found with id: " + userId);
        return userDto;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = userDao.updateUser(user);
        if (result)
            LOG.info("User was updated with id: " + user.getId());
        else
            LOG.warn("Unable to update user with id: " + user.getId());
        return result;
    }

    @Override
    public boolean deleteUser(int userId) {
        boolean result = userDao.deleteUser(userId);
        if (result)
            LOG.info("User was deleted with id: " + userId);
        else
            LOG.warn("Unable to delete user with id: " + userId);
        return result;
    }
}