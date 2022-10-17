package ua.com.epam.project.dao.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection con;

    @InjectMocks
    private UserDaoImpl userDao;

    private final User user;

    {
        user = new User();
        user.setId(1);
        user.setLogin("user");
        user.setFirstName("fName");
        user.setLastName("lName");
        user.setEmail("email@gmail.com");
        user.setPassword("pass");
        user.setCreated(new Date(1000));
        user.setStatus(Status.ACTIVE);
        user.setReset_password_token(null);
        user.setRoleId(2);
    }

    @Test
    void getInstance() {
        UserDao dao = UserDaoImpl.getInstance();
        assertNotNull(dao);
    }

    @Test
    void saveUser() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.execute())
                .thenReturn(true);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(userDao.saveUser(user));

        when(statement.execute())
                .thenThrow(SQLException.class);
        assertFalse(userDao.saveUser(user));
    }

    @Test
    void updateResetPasswordToken() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(userDao.updateResetPasswordToken("token", 1));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(userDao.updateResetPasswordToken("", -1));
    }

    @Test
    void updatePassword() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.execute())
                .thenReturn(true);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(userDao.updatePassword(user, "pass"));

        when(statement.execute())
                .thenThrow(SQLException.class);
        assertFalse(userDao.updatePassword(user, "pass"));
    }

    @Test
    void updateUser() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(userDao.updateUser(user));

        user.setPassword(null);
        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(userDao.updateUser(user));
    }

    @Test
    void deleteUser() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(userDao.deleteUser(1));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(userDao.deleteUser(-1));
    }

    @Test
    void getUserByLoginAndPassword() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(1);
        when(resultSet.getString(2))
                .thenReturn("user");
        when(resultSet.getString(3))
                .thenReturn("fName");
        when(resultSet.getString(4))
                .thenReturn("lName");
        when(resultSet.getString(5))
                .thenReturn("email@gmail.com");
        when(resultSet.getString(6))
                .thenReturn("pass");
        when(resultSet.getDate(7))
                .thenReturn(new java.sql.Date(1000));
        when(resultSet.getString(8))
                .thenReturn("ACTIVE");
        when(resultSet.getString(9))
                .thenReturn(null);
        when(resultSet.getInt(10))
                .thenReturn(2);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery())
                .thenReturn(resultSet);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        User expected = userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        assertEquals(expected, user);

        when(statement.executeQuery())
                .thenThrow(SQLException.class);
        expected = userDao.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        assertNull(expected);
    }
}