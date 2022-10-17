package ua.com.epam.project.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServiceFactoryTest {

    @Test
    void getUserService() {
        assertNotNull(ServiceFactory.getUserService());
    }

    @Test
    void getRoleService() {
        assertNotNull(ServiceFactory.getRoleService());
    }

    @Test
    void getTopicService() {
        assertNotNull(ServiceFactory.getTopicService());
    }

    @Test
    void getCourseService() {
        assertNotNull(ServiceFactory.getCourseService());
    }
}