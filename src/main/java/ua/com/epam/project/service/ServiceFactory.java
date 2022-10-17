package ua.com.epam.project.service;

import ua.com.epam.project.service.impl.CourseServiceImpl;
import ua.com.epam.project.service.impl.RoleServiceImpl;
import ua.com.epam.project.service.impl.TopicServiceImpl;
import ua.com.epam.project.service.impl.UserServiceImpl;

/**
 * Service factory.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class ServiceFactory {

    private ServiceFactory(){}

    /**
     * @return return implementation of user service
     */
    public static UserService getUserService() {
        return UserServiceImpl.getInstance();
    }

    /**
     * @return return implementation of role service
     */
    public static RoleService getRoleService() {
        return RoleServiceImpl.getInstance();
    }

    /**
     * @return return implementation of topic service
     */
    public static TopicService getTopicService() {
        return TopicServiceImpl.getInstance();
    }

    /**
     * @return return implementation of course service
     */
    public static CourseService getCourseService() {
        return CourseServiceImpl.getInstance();
    }
}