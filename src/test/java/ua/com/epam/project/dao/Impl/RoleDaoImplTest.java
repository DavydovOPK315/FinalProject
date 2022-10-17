package ua.com.epam.project.dao.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.RoleDao;
import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection con;

    @InjectMocks
    private RoleDaoImpl roleDao;

    private final Role roleAdmin;
    private final Role roleStudent;

    {
        roleAdmin = new Role();
        roleAdmin.setId(1);
        roleAdmin.setName("ADMIN");
        roleAdmin.setCreated(new Date(1000));
        roleAdmin.setStatus(Status.ACTIVE);

        roleStudent = new Role();
        roleStudent.setId(2);
        roleStudent.setName("STUDENT");
        roleStudent.setCreated(new Date(2000));
        roleStudent.setStatus(Status.ACTIVE);
    }


    @Test
    void getInstance() {
        RoleDao dao = RoleDaoImpl.getInstance();
        assertNotNull(dao);
    }

    @Test
    void getAll() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(1)
                .thenReturn(2);
        when(resultSet.getString(2))
                .thenReturn("ADMIN")
                .thenReturn("STUDENT");
        when(resultSet.getDate(3))
                .thenReturn(new Date(1000))
                .thenReturn(new Date(2000));
        when(resultSet.getString(4))
                .thenReturn("ACTIVE")
                .thenReturn("ACTIVE");
        Statement statement = mock(Statement.class);
        String query = "SELECT * FROM roles ORDER BY id;";
        when(statement.executeQuery(query))
                .thenReturn(resultSet);
        when(con.createStatement())
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        List<Role> expected = Arrays.asList(roleAdmin, roleStudent);
        List<Role> actual = roleDao.getAll();
        assertIterableEquals(expected, actual);

        when(con.createStatement())
                .thenThrow(SQLException.class);
        expected = roleDao.getAll();
        assertTrue(expected.isEmpty());
    }

    @Test
    void getRoleById() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(1);
        when(resultSet.getString(2))
                .thenReturn("ADMIN");
        when(resultSet.getDate(3))
                .thenReturn(new Date(1000));
        when(resultSet.getString(4))
                .thenReturn("ACTIVE");
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "SELECT * FROM roles r WHERE r.id = ?;";
        when(statement.executeQuery())
                .thenReturn(resultSet);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        Role expected = roleAdmin;
        Role actual = roleDao.getRoleById(1);
        assertEquals(expected, actual);

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        expected = roleDao.getRoleById(1);
        assertNull(expected);
    }

    @Test
    void updateRole() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "UPDATE roles r SET r.name = ?, r.status = ? WHERE r.id = ?;";
        when(statement.execute())
                .thenReturn(true);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(roleDao.updateRole(10, "STUDENT", "BANNED"));

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        assertFalse(roleDao.updateRole(-10, "STUDENT", "BANNED"));
    }

    @Test
    void createRole() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "INSERT INTO roles (name, status) values (?, ?)";
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(roleDao.createRole("STUDENT", "BANNED"));

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        assertFalse(roleDao.createRole("", ""));
    }

    @Test
    void deleteRole() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "DELETE FROM roles WHERE id = ?";
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(roleDao.deleteRole(10));

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        assertFalse(roleDao.deleteRole(-10));
    }
}