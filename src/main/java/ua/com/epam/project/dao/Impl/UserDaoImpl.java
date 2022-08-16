package ua.com.epam.project.dao.Impl;

import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static UserDao INSTANCE;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_INTO_USER = "INSERT INTO users (login, first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login = ? AND password = ?;";

    private UserDaoImpl() {
    }

    public static synchronized UserDao getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserDaoImpl();
        return INSTANCE;
    }


    @Override
    public boolean saveUser(User user) {

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_INTO_USER)) {

            String myPassword = user.getPassword();
            String mySecurePassword = PasswordUtil.encrypt(myPassword);

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, mySecurePassword);
            ps.setInt(6, 2);

            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String pass) {
        String decodePass = PasswordUtil.encrypt(pass);
        User user = null;
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {

            ps.setString(1, login);
            ps.setString(2, decodePass);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setPassword(rs.getString(6));
                user.setRoleId(rs.getInt(7));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
    }
}