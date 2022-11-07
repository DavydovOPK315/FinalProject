package ua.com.epam.project.dao.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.TopicDao;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.Topic;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class TopicDaoImplTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection con;

    @InjectMocks
    private TopicDaoImpl topicDao;

    private final Topic topicA;
    private final Topic topicB;

    {
        topicA = new Topic();
        topicA.setId(1);
        topicA.setName("topicA");
        topicA.setCreated(new Date(1000));
        topicA.setStatus(Status.ACTIVE);

        topicB = new Topic();
        topicB.setId(2);
        topicB.setName("topicB");
        topicB.setCreated(new Date(2000));
        topicB.setStatus(Status.BANNED);
    }

    @BeforeEach
    void setUp() throws Exception {
        setFinalStatic(TopicDaoImpl.class.getDeclaredField("connectionPool"), connectionPool);
    }

    @Test
    void getInstance() {
        TopicDao dao = TopicDaoImpl.getInstance();
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
                .thenReturn("topicA")
                .thenReturn("topicB");
        when(resultSet.getDate(3))
                .thenReturn(new Date(1000))
                .thenReturn(new Date(2000));
        when(resultSet.getString(4))
                .thenReturn("ACTIVE")
                .thenReturn("BANNED");
        Statement statement = mock(Statement.class);
        String query = "SELECT * FROM topics ORDER BY id;";
        when(statement.executeQuery(query))
                .thenReturn(resultSet);
        when(con.createStatement())
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        List<Topic> expected = Arrays.asList(topicA, topicB);
        List<Topic> actual = topicDao.getAll();
        assertIterableEquals(expected, actual);

        when(con.createStatement())
                .thenThrow(SQLException.class);
        expected = topicDao.getAll();
        assertTrue(expected.isEmpty());
    }

    @Test
    void getTopicById() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(2);
        when(resultSet.getString(2))
                .thenReturn("topicB");
        when(resultSet.getDate(3))
                .thenReturn(new Date(2000));
        when(resultSet.getString(4))
                .thenReturn("BANNED");

        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "SELECT * FROM topics r WHERE r.id = ?;";
        when(statement.executeQuery())
                .thenReturn(resultSet);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        Topic expected = topicB;
        Topic actual = topicDao.getTopicById(2);
        assertEquals(expected, actual);

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        expected = topicDao.getTopicById(2);
        assertNull(expected);
    }

    @Test
    void getTopicByName() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(2);
        when(resultSet.getString(2))
                .thenReturn("topicB");
        when(resultSet.getDate(3))
                .thenReturn(new Date(2000));
        when(resultSet.getString(4))
                .thenReturn("BANNED");

        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "SELECT * FROM topics r WHERE r.name = ?;";
        when(statement.executeQuery())
                .thenReturn(resultSet);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        Topic expected = topicB;
        Topic actual = topicDao.getTopicByName("topicB");
        assertEquals(expected, actual);

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        expected = topicDao.getTopicByName("topicB");
        assertNull(expected);
    }

    @Test
    void updateTopic() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "UPDATE topics r SET r.name = ?, r.status = ? WHERE r.id = ?;";
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(topicDao.updateTopic(2, "topicB", "BANNED"));

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        assertFalse(topicDao.updateTopic(-2, "topicB", "BANNED"));
    }

    @Test
    void createTopic() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "INSERT INTO topics (name, status) values (?, ?);";
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(topicDao.createTopic("topicB", "BANNED"));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(topicDao.createTopic("", null));
    }

    @Test
    void deleteTopic() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "DELETE FROM topics WHERE id = ?;";
        when(statement.executeUpdate())
                .thenReturn(1);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);
        assertTrue(topicDao.deleteTopic(1));

        when(statement.executeUpdate())
                .thenThrow(SQLException.class);
        assertFalse(topicDao.deleteTopic(-1));
    }

    @Test
    void getAllByCourseId() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(resultSet.getInt(1))
                .thenReturn(1)
                .thenReturn(2);
        when(resultSet.getString(2))
                .thenReturn("topicA")
                .thenReturn("topicB");
        when(resultSet.getDate(3))
                .thenReturn(new Date(1000))
                .thenReturn(new Date(2000));
        when(resultSet.getString(4))
                .thenReturn("ACTIVE")
                .thenReturn("BANNED");

        PreparedStatement statement = mock(PreparedStatement.class);
        String query = "SELECT t.*, ct.created FROM topics t, courses_has_topics ct WHERE ct.courses_id = ? AND ct.topics_id = t.id ORDER BY ct.created;";
        when(statement.executeQuery())
                .thenReturn(resultSet);
        when(con.prepareStatement(query))
                .thenReturn(statement);
        when(connectionPool.getConnection())
                .thenReturn(con);

        List<Topic> expected = Arrays.asList(topicA, topicB);
        List<Topic> actual = topicDao.getAllByCourseId(10);
        assertIterableEquals(expected, actual);

        when(con.prepareStatement(query))
                .thenThrow(SQLException.class);
        expected = topicDao.getAllByCourseId(10);
        assertTrue(expected.isEmpty());
    }
}