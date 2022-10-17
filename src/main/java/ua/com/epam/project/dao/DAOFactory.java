package ua.com.epam.project.dao;

import ua.com.epam.project.dao.Impl.CourseDaoImpl;
import ua.com.epam.project.dao.Impl.RoleDaoImpl;
import ua.com.epam.project.dao.Impl.TopicDaoImpl;
import ua.com.epam.project.dao.Impl.UserDaoImpl;

/**
 * DAO factory.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class DAOFactory {

    /**
     * Function to get user DAO
     *
     * @return user DAO
     */
    public static UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

    /**
     * Function to get role DAO
     *
     * @return role DAO
     */
    public static RoleDao getRoleDao() {
        return RoleDaoImpl.getInstance();
    }

    /**
     * Function to get topic DAO
     *
     * @return topic DAO
     */
    public static TopicDao getTopicDao() {
        return TopicDaoImpl.getInstance();
    }

    /**
     * Function to get course DAO
     *
     * @return course DAO
     */
    public static CourseDao getCourseDao() {
        return CourseDaoImpl.getInstance();
    }
}