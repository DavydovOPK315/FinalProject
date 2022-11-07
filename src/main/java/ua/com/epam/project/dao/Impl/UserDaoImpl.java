package ua.com.epam.project.dao.Impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.utils.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User DAO implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class UserDaoImpl implements UserDao {
    private static final String SELECT_USERS_WITH_ROLES = "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.created, u.status, r.name FROM users u JOIN roles r ON u.role_id = r.id ORDER BY u.id;";
    private static final String SELECT_USERS_WITH_ROLES_BY_STATUS = "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.created, u.status, r.name FROM users u JOIN roles r ON u.role_id = r.id AND u.status = ? ORDER BY u.id;";
    private static final String SELECT_USERS_WITH_ROLES_BY_ROLE = "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.created, u.status, r.name FROM users u JOIN roles r ON u.role_id = r.id AND r.name = ? ORDER BY u.id;";
    private static final String SELECT_USERS_WITH_ROLES_BY_LOGIN = "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.created, u.status, r.name FROM users u JOIN roles r ON u.role_id = r.id AND u.login LIKE ? ORDER BY u.id;";
    private static final String SELECT_USER_BY_ID = "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.created, u.status, r.name FROM users u JOIN roles r ON u.role_id = r.id AND u.id = ?;";
    private static final String INSERT_INTO_USER = "INSERT INTO users (login, first_name, last_name, email, password, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_INTO_USER_WITH_DEFAULT = "INSERT INTO users (login, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
    private static final String SELECT_USER_BY_RESET_TOKEN = "SELECT * FROM users u WHERE u.reset_password_token = ?;";
    private static final String UPDATE_USER_RESET_TOKEN_BY_ID = "UPDATE users u SET u.reset_password_token = ? WHERE u.id = ?;";
    private static final String UPDATE_USER_PASSWORD_BY_ID = "UPDATE users u SET u.reset_password_token = ?, u.password = ? WHERE u.id = ?;";
    private static final String UPDATE_USER_BY_ID_EXCEPT_PASSWORD = "UPDATE users u SET u.login = ?, u.first_name = ?, u.last_name = ?, u.email = ?, u.status = ?, u.role_id = ? WHERE u.id = ?;";
    private static final String UPDATE_USER_BY_ID = "UPDATE users u SET u.login = ?, u.first_name = ?, u.last_name = ?, u.email = ?, u.password = ? WHERE u.id = ?;";
    private static UserDao instance;
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    private UserDaoImpl() {
    }

    public static synchronized UserDao getInstance() {
        if (instance == null)
            instance = new UserDaoImpl();
        return instance;
    }

    @Override
    public boolean saveUser(User user) {
        String query = INSERT_INTO_USER_WITH_DEFAULT;

        if (user.getRoleId() != 0)
            query = INSERT_INTO_USER;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            String myPassword = user.getPassword();
            String mySecurePassword = PasswordUtil.encrypt(myPassword, user.getLogin());
            int k = 1;
            ps.setString(k++, user.getLogin());
            ps.setString(k++, user.getFirstName());
            ps.setString(k++, user.getLastName());
            ps.setString(k++, user.getEmail());
            ps.setString(k++, mySecurePassword);

            if (user.getRoleId() != 0) {
                ps.setString(k++, user.getStatus().name());
                ps.setInt(k, user.getRoleId());
            }
            ps.execute();
            return true;
        } catch (SQLException e) {
            LOG.error("saveUser: sql exception");
            return false;
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String pass) {
        User user = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            String decodePass = PasswordUtil.encrypt(pass, login);
            ps.setString(1, login);
            ps.setString(2, decodePass);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                user = getUserFromResultSet(rs);

            return user;
        } catch (SQLException e) {
            LOG.error("getUserByLoginAndPassword: sql exception");
            return user;
        }
    }

    @Override
    public boolean updateResetPasswordToken(String token, int userId) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_USER_RESET_TOKEN_BY_ID)) {
            ps.setString(1, token);
            ps.setInt(2, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("updateResetPasswordToken: sql exception");
            return false;
        }
    }

    @Override
    public User getUserByToken(String token) {
        return getUserByOption(token, SELECT_USER_BY_RESET_TOKEN);
    }

    @Override
    public User getUserByEmail(String email) {
        return getUserByOption(email, SELECT_USER_BY_EMAIL);
    }

    private User getUserByOption(String token, String selectUserByResetToken) {
        User user = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(selectUserByResetToken)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                user = getUserFromResultSet(rs);

            return user;
        } catch (SQLException e) {
            LOG.error("getUserByOption: sql exception");
            return user;
        }
    }

    @Override
    public boolean updatePassword(User user, String password) {
        user.setReset_password_token(null);

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_USER_PASSWORD_BY_ID)) {
            user.setPassword(PasswordUtil.encrypt(password, user.getLogin()));
            ps.setString(1, user.getReset_password_token());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            return ps.execute();
        } catch (SQLException e) {
            LOG.error("updatePassword: sql exception");
            return false;
        }
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_USERS_WITH_ROLES)) {

                while (rs.next()) {
                    UserDto userDto = getUserDtoFromResultSet(rs);
                    result.add(userDto);
                }
            }
            return result;
        } catch (SQLException e) {
            LOG.error("getAll: sql exception");
            return result;
        }
    }

    @Override
    public List<UserDto> getAllByStatus(String status) {
        List<UserDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USERS_WITH_ROLES_BY_STATUS)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    UserDto userDto = getUserDtoFromResultSet(rs);
                    result.add(userDto);
                }
            }
            return result;
        } catch (SQLException e) {
            LOG.error("getAllByStatus: sql exception");
            return result;
        }
    }

    @Override
    public List<UserDto> getAllByRole(String role) {
        List<UserDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USERS_WITH_ROLES_BY_ROLE)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    UserDto userDto = getUserDtoFromResultSet(rs);
                    result.add(userDto);
                }
            }
            return result;
        } catch (SQLException e) {
            LOG.error("getAllByRole: sql exception");
            return result;
        }
    }

    @Override
    public UserDto getUserByLogin(String login) {
        UserDto result = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USERS_WITH_ROLES_BY_LOGIN)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next())
                    result = getUserDtoFromResultSet(rs);

            }
            return result;
        } catch (SQLException e) {
            LOG.error("getUserByLogin: sql exception");
            return result;
        }
    }

    @Override
    public UserDto getUserById(int userId) {
        UserDto result = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next())
                    result = getUserDtoFromResultSet(rs);

                return result;
            }
        } catch (SQLException e) {
            LOG.error("getUserById: sql exception");
            return result;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String query;

        if (user.getPassword() != null)
            query = UPDATE_USER_BY_ID;
        else
            query = UPDATE_USER_BY_ID_EXCEPT_PASSWORD;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            int k = 1;
            ps.setString(k++, user.getLogin());
            ps.setString(k++, user.getFirstName());
            ps.setString(k++, user.getLastName());
            ps.setString(k++, user.getEmail());

            if (user.getPassword() != null) {
                user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getLogin()));
                ps.setString(k++, user.getPassword());
            } else {
                ps.setString(k++, user.getStatus().name());
                ps.setInt(k++, user.getRoleId());
            }

            ps.setInt(k, user.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("updateUser: sql exception");
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id = ?;")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("deleteUser: sql exception");
            return false;
        }
    }

    private UserDto getUserDtoFromResultSet(ResultSet rs) {
        UserDto userDto = null;

        try {
            int k = 1;
            userDto = new UserDto();
            userDto.setId(rs.getInt(k++));
            userDto.setLogin(rs.getString(k++));
            userDto.setFirstName(rs.getString(k++));
            userDto.setLastName(rs.getString(k++));
            userDto.setEmail(rs.getString(k++));
            userDto.setCreated(rs.getDate(k++));
            userDto.setStatus(rs.getString(k++));
            userDto.setRole(rs.getString(k));
            return userDto;
        } catch (SQLException e) {
            LOG.error("getUserDtoFromResultSet: sql exception");
            return userDto;
        }
    }

    private User getUserFromResultSet(ResultSet rs) {
        User user = null;

        try {
            user = new User();
            int k = 1;
            user.setId(rs.getInt(k++));
            user.setLogin(rs.getString(k++));
            user.setFirstName(rs.getString(k++));
            user.setLastName(rs.getString(k++));
            user.setEmail(rs.getString(k++));
            user.setPassword(rs.getString(k++));
            user.setCreated(rs.getDate(k++));
            user.setStatus(Status.valueOf(rs.getString(k++)));
            user.setReset_password_token(rs.getString(k++));
            user.setRoleId(rs.getInt(k));
            return user;
        } catch (SQLException e) {
            LOG.error("getUserFromResultSet: sql exception");
            return user;
        }
    }
}