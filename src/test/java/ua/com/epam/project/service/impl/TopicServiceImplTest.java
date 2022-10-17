package ua.com.epam.project.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.TopicDao;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.service.TopicService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    @Mock
    private TopicDao topicDao;

    @InjectMocks
    private TopicServiceImpl topicService;

    private final Topic topic;

    {
        topic = new Topic();
        topic.setId(10);
        topic.setName("BBC");
        topic.setStatus(Status.ACTIVE);
    }

    @Test
    void getInstance() {
        TopicService topicService = TopicServiceImpl.getInstance();
        Assertions.assertNotNull(topicService);
    }

    @Test
    void getAll() {
        List<Topic> topics = Collections.singletonList(topic);

        when(topicDao.getAll()).thenReturn(topics);

        Assertions.assertNotNull(topicService.getAll());
        Assertions.assertEquals(1, topicService.getAll().size());
    }

    @Test
    void getAllByCourseId() {
        List<Topic> topics = Collections.singletonList(topic);

        when(topicDao.getAllByCourseId(5)).thenReturn(topics);
        Assertions.assertNotNull(topicService.getAllByCourseId(5));
    }

    @Test
    void getTopicById() {
        when(topicDao.getTopicById(10)).thenReturn(topic);
        when(topicDao.getTopicById(-10)).thenReturn(null);

        Assertions.assertNotNull(topicService.getTopicById(10));
        Assertions.assertEquals(10, topicService.getTopicById(10).getId());
        Assertions.assertNull(topicService.getTopicById(-10));
    }

    @Test
    void getTopicByName() {
        when(topicDao.getTopicByName("BBC")).thenReturn(topic);
        when(topicDao.getTopicByName("AAA")).thenReturn(null);

        Assertions.assertNotNull(topicService.getTopicByName("BBC"));
        Assertions.assertEquals("BBC", topicService.getTopicByName("BBC").getName());
        Assertions.assertNull(topicService.getTopicByName("AAA"));
    }

    @Test
    void updateTopic() {
        when(topicDao.updateTopic(10, "Crypto", "ACTIVE")).thenReturn(true);
        when(topicDao.updateTopic(-10, "Crypto", "ACTIVE")).thenReturn(false);

        Assertions.assertTrue(topicService.updateTopic(10, "Crypto", "ACTIVE"));
        Assertions.assertFalse(topicService.updateTopic(-10, "Crypto", "ACTIVE"));
    }

    @Test
    void createTopic() {
        when(topicDao.createTopic("Crypto", "ACTIVE")).thenReturn(true);
        when(topicDao.createTopic("", "ACTIVE")).thenReturn(false);

        Assertions.assertTrue(topicService.createTopic("Crypto", "ACTIVE"));
        Assertions.assertFalse(topicService.createTopic("", "ACTIVE"));
    }

    @Test
    void deleteTopic() {
        when(topicDao.deleteTopic(10)).thenReturn(true);
        when(topicDao.deleteTopic(-10)).thenReturn(false);

        Assertions.assertTrue(topicService.deleteTopic(10));
        Assertions.assertFalse(topicService.deleteTopic(-10));
    }
}