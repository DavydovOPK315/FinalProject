package ua.com.epam.project.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.RoleDao;
import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.service.RoleService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleServiceImpl service;

    private final Role role;

    {
        role = new Role();
        role.setId(10);
        role.setName("MANAGER");
        role.setStatus(Status.ACTIVE);
    }

    @BeforeEach
    void setUp() throws Exception {
        setFinalStatic(RoleServiceImpl.class.getDeclaredField("roleDao"), roleDao);
    }

    @Test
    void getInstance() {
        RoleService roleService = RoleServiceImpl.getInstance();
        Assertions.assertNotNull(roleService);
    }

    @Test
    void getAll() {
        List<Role> roles = Collections.singletonList(role);

        when(roleDao.getAll()).thenReturn(roles);

        Assertions.assertNotNull(service.getAll());
        Assertions.assertEquals(1, service.getAll().size());
    }

    @Test
    void getRoleById() {
        when(roleDao.getRoleById(10)).thenReturn(role);
        when(roleDao.getRoleById(-20)).thenReturn(null);

        Assertions.assertNotNull(service.getRoleById(10));
        Assertions.assertEquals(10, service.getRoleById(10).getId());
        Assertions.assertNull(service.getRoleById(-20));
    }

    @Test
    void updateRole() {
        when(roleDao.updateRole(role.getId(), role.getName(), role.getStatus().name())).thenReturn(true);
        when(roleDao.updateRole(-20, role.getName(), role.getStatus().name())).thenReturn(false);

        Assertions.assertTrue(service.updateRole(role.getId(), role.getName(), role.getStatus().name()));
        Assertions.assertFalse(service.updateRole(-20, role.getName(), role.getStatus().name()));
    }

    @Test
    void createRole() {
        when(roleDao.createRole("MANAGER", "ACTIVE")).thenReturn(true);
        when(roleDao.createRole(null, "")).thenReturn(false);

        Assertions.assertTrue(service.createRole("MANAGER", "ACTIVE"));
        Assertions.assertFalse(service.createRole(null, ""));
    }

    @Test
    void deleteRole() {
        when(roleDao.deleteRole(10)).thenReturn(true);
        when(roleDao.deleteRole(-10)).thenReturn(false);

        Assertions.assertTrue(service.deleteRole(10));
        Assertions.assertFalse(service.deleteRole(-10));
    }
}