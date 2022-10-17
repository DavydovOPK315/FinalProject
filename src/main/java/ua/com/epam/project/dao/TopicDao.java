package ua.com.epam.project.dao;

import ua.com.epam.project.entity.Topic;

import java.util.List;

/**
 * Topic DAO
 *
 * @author Denis Davydov
 * @version 2.0
 */
public interface TopicDao {

    /**
     * Function to create topic with name and status
     *
     * @param name   topic name
     * @param status topic status
     * @return return query result can be true or false
     */
    boolean createTopic(String name, String status);

    /**
     * Function to update topic by topic ID, topic name and topic status
     *
     * @param topicId topic ID
     * @param name    topic name
     * @param status  topic status
     * @return return query result can be true or false
     */
    boolean updateTopic(int topicId, String name, String status);

    /**
     * Function to delete topic by topic ID
     *
     * @param topicId topic ID
     * @return return query result can be true or false
     */
    boolean deleteTopic(int topicId);

    /**
     * Function to get all topic
     *
     * @return return all topics
     * @see #getAllByCourseId(int)
     * @see #getTopicById(int)
     * @see #getTopicByName(String)
     */
    List<Topic> getAll();

    /**
     * Function to get all topics by course ID
     *
     * @param courseId course ID
     * @return return all topics by course ID
     * @see #getAll()
     * @see #getTopicById(int)
     * @see #getTopicByName(String)
     */
    List<Topic> getAllByCourseId(int courseId);

    /**
     * Function to get topic by ID
     *
     * @param topicId topic ID
     * @return return topic by topic ID
     * @see #getAllByCourseId(int)
     * @see #getAll()
     * @see #getTopicByName(String)
     */
    Topic getTopicById(int topicId);

    /**
     * Function to get topic by name
     *
     * @param name topic name
     * @return return topic by name
     * @see #getAllByCourseId(int)
     * @see #getTopicById(int)
     * @see #getAll()
     */
    Topic getTopicByName(String name);
}