package ua.com.epam.project.dao;

import ua.com.epam.project.entity.Role;

import java.util.List;

/**
 * Role DAO
 *
 * @author Denis Davydov
 * @version 2.0
 */
public interface RoleDao {

    /**
     * Function to create role with name and status
     *
     * @param name   name
     * @param status status
     * @return return query result can be true or false
     */
    boolean createRole(String name, String status);

    /**
     * Function to update role by ID, name and status
     *
     * @param roleId ID
     * @param name   name
     * @param status status
     * @return return query result can be true or false
     */
    boolean updateRole(int roleId, String name, String status);

    /**
     * Function to delete role by role ID
     *
     * @param roleId role ID
     * @return return query result can be true or false
     */
    boolean deleteRole(int roleId);

    /**
     * Function to get all roles
     *
     * @return return all roles
     * @see #getRoleById(int)
     */
    List<Role> getAll();

    /**
     * Function to get role by role ID
     *
     * @param roleId role ID
     * @return return role by role ID
     * @see #getAll()
     */
    Role getRoleById(int roleId);
}