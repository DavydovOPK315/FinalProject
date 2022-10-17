package ua.com.epam.project.dao.Impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.RoleDao;
import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private static final String SELECT_ROLES = "SELECT * FROM roles ORDER BY id;";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles r WHERE r.id = ?;";
    private static final String UPDATE_ROLE_NAME_AND_STATUS_BY_ID = "UPDATE roles r SET r.name = ?, r.status = ? WHERE r.id = ?;";
    private static final String INSERT_INTO_ROLES_NAME_STATUS = "INSERT INTO roles (name, status) values (?, ?)";
    private static final String DELETE_FROM_ROLES_BY_ID = "DELETE FROM roles WHERE id = ?";
    private static RoleDao instance;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOG = Logger.getLogger(RoleDaoImpl.class);

    private RoleDaoImpl() {
    }

    public static synchronized RoleDao getInstance() {
        if (instance == null)
            instance = new RoleDaoImpl();
        return instance;
    }

    @Override
    public List<Role> getAll() {
        List<Role> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_ROLES)) {
                while (rs.next()) {
                    Role role = new Role();
                    int k = 1;
                    role.setId(rs.getInt(k++));
                    role.setName(rs.getString(k++));
                    role.setCreated(rs.getDate(k++));
                    role.setStatus(Status.valueOf(rs.getString(k)));
                    result.add(role);
                }
                return result;
            }
        } catch (SQLException e) {
            LOG.error("getAll: sql exception");
            return result;
        }
    }

    @Override
    public Role getRoleById(int roleId) {
        Role role = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ROLE_BY_ID)) {
            ps.setInt(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int k = 1;
                    role = new Role();
                    role.setId(rs.getInt(k++));
                    role.setName(rs.getString(k++));
                    role.setCreated(rs.getDate(k++));
                    role.setStatus(Status.valueOf(rs.getString(k)));
                }
                return role;
            }
        } catch (SQLException e) {
            LOG.error("getRoleById: sql exception");
            return role;
        }
    }

    @Override
    public boolean updateRole(int roleId, String name, String status) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ROLE_NAME_AND_STATUS_BY_ID)) {
            ps.setString(1, name);
            ps.setString(2, status);
            ps.setInt(3, roleId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            LOG.error("updateRole: sql exception");
            return false;
        }
    }

    @Override
    public boolean createRole(String name, String status) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_INTO_ROLES_NAME_STATUS)) {
            ps.setString(1, name);
            ps.setString(2, status);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("createRole: sql exception");
            return false;
        }
    }

    @Override
    public boolean deleteRole(int roleId) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_FROM_ROLES_BY_ID)) {
            ps.setInt(1, roleId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("deleteRole: sql exception");
            return false;
        }
    }
}