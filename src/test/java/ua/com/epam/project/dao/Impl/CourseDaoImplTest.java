package ua.com.epam.project.dao.Impl;

import org.junit.jupiter.api.BeforeEach;
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
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class CourseDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection con;

    @Mock
    private UserService userService;

    @Mock
    private TopicService topicService;

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

    @BeforeEach
    void setUp() throws Exception {
        setFinalStatic(CourseDaoImpl.class.getDeclaredField("connectionPool"), connectionPool);
        setFinalStatic(CourseDaoImpl.class.getDeclaredField("userService"), userService);
        setFinalStatic(CourseDaoImpl.class.getDeclaredField("topicService"), topicService);
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
    void getCourseById() throws SQLException {
        doReturn(new ArrayList<>()).when(courseDao)
                .getAllStudentsWithGradesByCourseId(1);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(1);
        when(resultSet.getString(2))
                .thenReturn("courseName");
        when(resultSet.getDate(3))
                .thenReturn(new java.sql.Date(1000));
        when(resultSet.getDate(4))
                .thenReturn(new java.sql.Date(10000));
        when(resultSet.getString(5))
                .thenReturn("desc");
        when(resultSet.getDate(6))
                .thenReturn(new java.sql.Date(100));
        when(resultSet.getString(7))
                .thenReturn("ACTIVE");
        when(resultSet.getString(8))
                .thenReturn("login");

        Statement statement2 = mock(Statement.class);
        when(statement2.executeQuery(anyString()))
                .thenReturn(null);

        doReturn(statement2).when(con)
                .createStatement();

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery())
                .thenReturn(resultSet);

        doReturn(statement).when(con)
                .prepareStatement(anyString());
        when(connectionPool.getConnection())
                .thenReturn(con);

        CourseDto course = new CourseDto();
        course.setId(1);
        course.setName("courseName");
        course.setDateStart(new java.sql.Date(1000));
        course.setDateEnd(new java.sql.Date(10000));
        course.setDescription("desc");
        course.setCreated(new java.sql.Date(100));
        course.setStatus("ACTIVE");
        course.setTeacherLogin("login");
        course.setNumberStudents(0);

        CourseDto result = courseDao.getCourseById(1);

        assertEquals(course.getId(), result.getId());
        assertEquals(course.getName(), result.getName());
        assertEquals(course.getDateStart(), result.getDateStart());
        assertEquals(course.getDateEnd(), result.getDateEnd());
        assertEquals(course.getDescription(), result.getDescription());
        assertEquals(course.getCreated(), result.getCreated());
        assertEquals(course.getStatus(), result.getStatus());
        assertEquals(course.getTeacherLogin(), result.getTeacherLogin());
        assertEquals(course.getNumberStudents(), result.getNumberStudents());

        verify(connectionPool, times(1)).getConnection();
        verify(con, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(courseDao, times(1)).getAllStudentsWithGradesByCourseId(1);
    }

    @Test
    void getCourseByIdFail() throws SQLException {
        when(connectionPool.getConnection())
                .thenReturn(con);
        when(con.createStatement())
                .thenThrow(SQLException.class);

        CourseDto expected = courseDao.getCourseById(1);
        assertNull(expected);
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