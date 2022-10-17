package ua.com.epam.project.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.UserDao;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl service;

    private final User user;
    private final UserDto userDto;
    private final List<UserDto> userDtoList;

    {
        user = new User();
        userDto = new UserDto();
        user.setId(10);
        user.setLogin("guest");
        userDto.setId(10);
        userDto.setLogin("guest");

        userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
    }

    @Test
    void getInstance() {
        UserService userService = UserServiceImpl.getInstance();
        Assertions.assertNotNull(userService);
    }

    @Test
    void registerUser() {
        User registered = new User();
        registered.setLogin("admin");

        when(userDao.saveUser(user)).thenReturn(true);
        when(userDao.saveUser(registered)).thenReturn(false);

        assertTrue(service.registerUser(user));
        assertFalse(service.registerUser(registered));
    }

    @Test
    void getUserByLoginAndPassword() {
        when(userDao.getUserByLoginAndPassword("guest", "pass")).thenReturn(user);
        when(userDao.getUserByLoginAndPassword("noname", "")).thenReturn(null);

        assertNotNull(service.getUserByLoginAndPassword("guest", "pass"));
        assertNull(service.getUserByLoginAndPassword("noname", ""));
    }

    @Test
    void getUserByEmail() {
        when(userDao.getUserByEmail("email@gmail.com")).thenReturn(user);
        when(userDao.getUserByEmail("")).thenReturn(null);

        assertNotNull(service.getUserByEmail("email@gmail.com"));
        assertNull(service.getUserByEmail(""));
    }

    @Test
    void updateResetPasswordToken() {
        when(userDao.updateResetPasswordToken("token", 10)).thenReturn(true);
        when(userDao.updateResetPasswordToken("", -10)).thenReturn(false);

        assertTrue(service.updateResetPasswordToken("token", 10));
        assertFalse(service.updateResetPasswordToken("", -10));
    }

    @Test
    void getUserByToken() {
        when(userDao.getUserByToken("token")).thenReturn(user);
        when(userDao.getUserByToken("")).thenReturn(null);

        assertNotNull(service.getUserByToken("token"));
        assertNull(service.getUserByToken(""));
    }

    @Test
    void updatePassword() {
        when(userDao.updatePassword(user, "pass")).thenReturn(true);
        when(userDao.updatePassword(user, "")).thenReturn(false);

        assertTrue(service.updatePassword(user, "pass"));
        assertFalse(service.updatePassword(user, ""));
    }

    @Test
    void getAll() {
        when(userDao.getAll()).thenReturn(userDtoList);
        assertEquals(1, service.getAll().size());
    }

    @Test
    void getAllByStatus() {
        when(userDao.getAllByStatus("ACTIVE")).thenReturn(userDtoList);
        assertEquals(1, service.getAllByStatus("ACTIVE").size());
    }

    @Test
    void getAllByRole() {
        when(userDao.getAllByRole("STUDENT")).thenReturn(userDtoList);
        assertEquals(1, service.getAllByRole("STUDENT").size());
    }

    @Test
    void getUserByLogin() {
        when(userDao.getUserByLogin("guest")).thenReturn(userDto);
        when(userDao.getUserByLogin("noname")).thenReturn(null);

        assertEquals(userDto, service.getUserByLogin("guest"));
        assertNull(service.getUserByLogin("noname"));
    }

    @Test
    void getUserById() {
        when(userDao.getUserById(10)).thenReturn(userDto);
        when(userDao.getUserById(-10)).thenReturn(null);

        assertEquals(10, service.getUserById(10).getId());
        assertNull(service.getUserById(-10));
    }

    @Test
    void updateUser() {
        User emptyUser = new User();
        emptyUser.setFirstName("empty");

        when(userDao.updateUser(user)).thenReturn(true);
        when(userDao.updateUser(emptyUser)).thenReturn(false);

        assertTrue(service.updateUser(user));
        assertFalse(service.updateUser(emptyUser));
    }

    @Test
    void deleteUser() {
        when(userDao.deleteUser(10)).thenReturn(true);
        when(userDao.deleteUser(-10)).thenReturn(false);

        assertTrue(service.deleteUser(10));
        assertFalse(service.deleteUser(-10));
    }
}