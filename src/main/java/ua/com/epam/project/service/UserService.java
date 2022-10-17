package ua.com.epam.project.service;

import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;

import java.util.List;

/**
 * User service
 *
 * @author Denis Davydov
 * @version 2.0
 */
public interface UserService {
    /**
     * Function to register user
     *
     * @param user user object with information about it
     * @return return query result can be true or false
     */
    boolean registerUser(User user);

    /**
     * Function to update user token by user ID
     *
     * @param token  - password reset token
     * @param userId - password reset user ID
     * @return return query result can be true or false
     */
    boolean updateResetPasswordToken(String token, int userId);

    /**
     * Function to update user password by user
     *
     * @param user     - update password user object
     * @param password - update password
     * @return return query result can be true or false
     */
    boolean updatePassword(User user, String password);

    /**
     * Function to update user
     *
     * @param user - user to update
     * @return return query result can be true or false
     */
    boolean updateUser(User user);

    /**
     * Function to delete user by user ID
     *
     * @param userId - user ID to delete
     * @return return query result can be true or false
     */
    boolean deleteUser(int userId);

    /**
     * Function to get user by login and password
     *
     * @param login - user login
     * @param pass  - user password
     * @return return user by login and password
     */
    User getUserByLoginAndPassword(String login, String pass);

    /**
     * Function to get user by email
     *
     * @param email - user email
     * @return return user by email
     */
    User getUserByEmail(String email);

    /**
     * Function to get user by token
     *
     * @param token - user token
     * @return return user by token
     */
    User getUserByToken(String token);

    /**
     * Function to get all users
     *
     * @return return all users
     */
    List<UserDto> getAll();

    /**
     * Function to get all users by status
     *
     * @param status - user status
     * @return return all users by status
     */
    List<UserDto> getAllByStatus(String status);

    /**
     * Function to get all users by role
     *
     * @param role - user role
     * @return return all users by role
     */
    List<UserDto> getAllByRole(String role);

    /**
     * Function to get user by login
     *
     * @param login - user login
     * @return return user by login
     */
    UserDto getUserByLogin(String login);

    /**
     * Function to get user by user ID
     *
     * @param userId - user ID
     * @return return user by ID
     */
    UserDto getUserById(int userId);
}