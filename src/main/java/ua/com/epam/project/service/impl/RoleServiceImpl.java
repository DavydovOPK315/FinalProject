package ua.com.epam.project.service.impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.DAOFactory;
import ua.com.epam.project.dao.RoleDao;
import ua.com.epam.project.entity.Role;
import ua.com.epam.project.service.RoleService;

import java.util.List;

/**
 * Role service implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = DAOFactory.getRoleDao();
    private static RoleService instance;
    private static final Logger LOG = Logger.getLogger(RoleServiceImpl.class);

    private RoleServiceImpl() {
    }

    public static synchronized RoleService getInstance() {
        if (instance == null)
            instance = new RoleServiceImpl();
        return instance;
    }

    @Override
    public List<Role> getAll() {
        List<Role> result = roleDao.getAll();
        LOG.info(result.size() + " roles were found");
        return result;
    }

    @Override
    public Role getRoleById(int roleId) {
        Role result = roleDao.getRoleById(roleId);
        if (result != null)
            LOG.info("Role: " + result.getName() + " were found");
        else
            LOG.info("No role found by id: " + roleId);
        return result;
    }

    @Override
    public boolean updateRole(int roleId, String name, String status) {
        boolean result = roleDao.updateRole(roleId, name, status);
        if (result)
            LOG.info("Role has been updated with id: " + roleId);
        else
            LOG.warn("Unable to update the role with id: " + roleId);
        return result;
    }

    @Override
    public boolean createRole(String name, String status) {
        boolean result = roleDao.createRole(name, status);
        if (result)
            LOG.info("Role has been created with name: " + name);
        else
            LOG.warn("Unable to create the role with name: " + name);
        return result;
    }

    @Override
    public boolean deleteRole(int roleId) {
        boolean result = roleDao.deleteRole(roleId);
        if (result)
            LOG.info("Role has been deleted with id: " + roleId);
        else
            LOG.warn("Unable to delete the role with id: " + roleId);
        return result;
    }
}
