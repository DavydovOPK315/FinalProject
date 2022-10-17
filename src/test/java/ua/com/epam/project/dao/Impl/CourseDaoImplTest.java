package ua.com.epam.project.dao.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.CourseDao;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.service.UserService;

import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection con;

    @Mock
    private UserService userService;

    @Spy
    @InjectMocks
    private CourseDaoImpl courseDao;

    private final CourseDto courseDto;
    private final UserDto userDto;

    {
        String[] topics = new String[]{"1"};

        courseDto = new CourseDto();
        courseDto.setId(10);
        courseDto.setDateStart(new Date());
        courseDto.setDateEnd(new Date());
        courseDto.setTeacherLogin("teacher");
        courseDto.setTopics(topics);

        userDto = new UserDto();
        userDto.setId(10);
        userDto.setLogin("teacher");
    }

    @Test
    void getInstance() {
        CourseDao dao = CourseDaoImpl.getInstance();
        assertNotNull(dao);
    }

    @Test
    void createCourse() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.getGeneratedKeys())
                .thenReturn(resultSet);
        when(statement.executeUpdate())
                .thenReturn(1);
        doReturn(statement).when(con)
                .prepareStatement("INSERT INTO courses (name, date_start, date_end, description) values (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        doReturn(statement).when(con)
                .prepareStatement(any(String.class));
        when(connectionPool.getConnection())
                .thenReturn(con);
        when(userService.getUserByLogin(courseDto.getTeacherLogin()))
                .thenReturn(userDto);
        assertTrue(courseDao.createCourse(courseDto));

        when(resultSet.next())
                .thenReturn(false);
        assertFalse(courseDao.createCourse(courseDto));
    }

    @Test
    void updateCourse() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        doReturn(courseDto).when(courseDao).getCourseById(anyInt());
        when(userService.getUserByLogin(courseDto.getTeacherLogin())).thenReturn(userDto);
        assertTrue(courseDao.updateCourse(courseDto));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(courseDao.updateCourse(courseDto));
    }

    @Test
    void deleteTopicFromCourse() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(courseDao.deleteTopicFromCourse(1, 2));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(courseDao.deleteTopicFromCourse(-1, -2));
    }

    @Test
    void leaveCourse() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(courseDao.leaveCourse(1, 2));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(courseDao.leaveCourse(-1, -2));
    }

    @Test
    void deleteCourse() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        when(con.prepareStatement(anyString()))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(courseDao.deleteCourse(1));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(courseDao.deleteCourse(-1));
    }
}