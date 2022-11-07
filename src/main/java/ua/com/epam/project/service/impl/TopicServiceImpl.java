package ua.com.epam.project.service.impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.DAOFactory;
import ua.com.epam.project.dao.TopicDao;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.service.TopicService;

import java.util.List;

/**
 * Topic service implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class TopicServiceImpl implements TopicService {
    private static final TopicDao topicDao = DAOFactory.getTopicDao();
    private static TopicService instance;
    private static final Logger LOG = Logger.getLogger(TopicServiceImpl.class);

    private TopicServiceImpl() {
    }

    public static synchronized TopicService getInstance() {
        if (instance == null)
            instance = new TopicServiceImpl();
        return instance;
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> result = topicDao.getAll();
        LOG.info(result.size() + " topics were found");
        return result;
    }

    @Override
    public List<Topic> getAllByCourseId(int courseId) {
        List<Topic> result = topicDao.getAllByCourseId(courseId);
        LOG.info(result.size() + " topics were found");
        return result;
    }

    @Override
    public Topic getTopicById(int topicId) {
        Topic result = topicDao.getTopicById(topicId);
        if (result != null)
            LOG.info("Topic: " + result.getName() + " were found");
        else
            LOG.info("No topic found by id: " + topicId);
        return result;
    }

    @Override
    public Topic getTopicByName(String name) {
        Topic result = topicDao.getTopicByName(name);
        if (result != null)
            LOG.info("Topic: " + result.getName() + " were found");
        else
            LOG.info("No topic found by name: " + name);
        return result;
    }

    @Override
    public boolean updateTopic(int topicId, String name, String status) {
        boolean result = topicDao.updateTopic(topicId, name, status);
        if (result)
            LOG.info("Topic has been updated with id: " + topicId);
        else
            LOG.warn("Unable to update the topic with id: " + topicId);
        return result;
    }

    @Override
    public boolean createTopic(String name, String status) {
        boolean result = topicDao.createTopic(name, status);
        if (result)
            LOG.info("Topic has been created with name: " + name);
        else
            LOG.warn("Unable to create the topic with name: " + name);
        return result;
    }

    @Override
    public boolean deleteTopic(int topicId) {
        boolean result = topicDao.deleteTopic(topicId);
        if (result)
            LOG.info("Topic has been deleted with id: " + topicId);
        else
            LOG.warn("Unable to delete the topic with id: " + topicId);
        return result;
    }
}