package ua.com.epam.project.dao.Impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.TopicDao;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.entity.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Topic DAO implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class TopicDaoImpl implements TopicDao {
    private static final String SELECT_TOPICS = "SELECT * FROM topics ORDER BY id;";
    private static final String SELECT_TOPIC_BY_ID = "SELECT * FROM topics r WHERE r.id = ?;";
    private static final String SELECT_TOPIC_BY_NAME = "SELECT * FROM topics r WHERE r.name = ?;";
    private static final String UPDATE_TOPIC_NAME_AND_STATUS_BY_ID = "UPDATE topics r SET r.name = ?, r.status = ? WHERE r.id = ?;";
    private static final String INSERT_INTO_TOPICS_NAME_STATUS = "INSERT INTO topics (name, status) values (?, ?);";
    private static final String DELETE_FROM_TOPICS_BY_ID = "DELETE FROM topics WHERE id = ?;";
    private static final String SELECT_TOPICS_BY_COURSE_ID = "SELECT t.*, ct.created FROM topics t, courses_has_topics ct WHERE ct.courses_id = ? AND ct.topics_id = t.id ORDER BY ct.created;";
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static TopicDao instance;
    private static final Logger LOG = Logger.getLogger(TopicDaoImpl.class);

    private TopicDaoImpl() {
    }

    public static synchronized TopicDao getInstance() {
        if (instance == null)
            instance = new TopicDaoImpl();
        return instance;
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SELECT_TOPICS)) {
                return getTopicsFromResultSet(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAll: sql exception");
            return result;
        }
    }

    private List<Topic> getTopicsFromResultSet(List<Topic> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Topic topic = new Topic();
            int k = 1;
            topic.setId(rs.getInt(k++));
            topic.setName(rs.getString(k++));
            topic.setCreated(rs.getDate(k++));
            topic.setStatus(Status.valueOf(rs.getString(k)));
            result.add(topic);
        }
        return result;
    }

    @Override
    public Topic getTopicById(int topicId) {
        Topic topic = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_TOPIC_BY_ID)) {
            ps.setInt(1, topicId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int k = 1;
                    topic = new Topic();
                    topic.setId(rs.getInt(k++));
                    topic.setName(rs.getString(k++));
                    topic.setCreated(rs.getDate(k++));
                    topic.setStatus(Status.valueOf(rs.getString(k)));
                }
                return topic;
            }
        } catch (SQLException e) {
            LOG.error("getTopicById: sql exception");
            return topic;
        }
    }

    @Override
    public Topic getTopicByName(String name) {
        Topic topic = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_TOPIC_BY_NAME)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int k = 1;
                    topic = new Topic();
                    topic.setId(rs.getInt(k++));
                    topic.setName(rs.getString(k++));
                    topic.setCreated(rs.getDate(k++));
                    topic.setStatus(Status.valueOf(rs.getString(k)));
                }
                return topic;
            }
        } catch (SQLException e) {
            LOG.error("getTopicByName: sql exception");
            return topic;
        }
    }

    @Override
    public boolean updateTopic(int topicId, String name, String status) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_TOPIC_NAME_AND_STATUS_BY_ID)) {
            ps.setString(1, name);
            ps.setString(2, status);
            ps.setInt(3, topicId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("updateTopic: sql exception");
            return false;
        }
    }

    @Override
    public boolean createTopic(String name, String status) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_INTO_TOPICS_NAME_STATUS)) {
            ps.setString(1, name);
            ps.setString(2, status);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("createTopic: sql exception");
            return false;
        }
    }

    @Override
    public boolean deleteTopic(int topicId) {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_FROM_TOPICS_BY_ID)) {
            ps.setInt(1, topicId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error("deleteTopic: sql exception");
            return false;
        }
    }

    @Override
    public List<Topic> getAllByCourseId(int courseId) {
        List<Topic> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_TOPICS_BY_COURSE_ID)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                return getTopicsFromResultSet(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByCourseId: sql exception");
            return result;
        }
    }
}