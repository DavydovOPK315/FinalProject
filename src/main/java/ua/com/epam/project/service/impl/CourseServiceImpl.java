package ua.com.epam.project.service.impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.CourseDao;
import ua.com.epam.project.dao.DAOFactory;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.service.CourseService;

import java.sql.Date;
import java.util.List;

/**
 * Course service implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class CourseServiceImpl implements CourseService {
    private static CourseService instance;
    private CourseDao courseDao = DAOFactory.getCourseDao();
    private static final Logger LOG = Logger.getLogger(CourseServiceImpl.class);

    private CourseServiceImpl() {
    }

    public static CourseService getInstance() {
        if (instance == null)
            instance = new CourseServiceImpl();
        return instance;
    }

    @Override
    public boolean createCourse(CourseDto courseDto) {
        boolean result = courseDao.createCourse(courseDto);
        if (result)
            LOG.info("Course has been created with name: " + courseDto.getName());
        else
            LOG.warn("Unable to create the course with name: " + courseDto.getName());
        return result;
    }


    @Override
    public boolean updateCourse(CourseDto course) {
        boolean result = courseDao.updateCourse(course);
        if (result)
            LOG.info("Course has been updated with name: " + course.getName());
        else
            LOG.warn("Unable to update the course with name: " + course.getName());
        return result;
    }

    @Override
    public boolean updateGrades(int courseId, String[] studentsId, String[] grades) {
        boolean result = courseDao.updateGrades(courseId, studentsId, grades);
        if (result)
            LOG.info("Course grades has been updated with id: " + courseId);
        else
            LOG.warn("Failed to update course grades. It must be between 60 and 100 or equal to 0. Course with id: " + courseId);
        return result;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        boolean result = courseDao.deleteCourse(courseId);
        if (result)
            LOG.info("Course has been deleted with id: " + courseId);
        else
            LOG.warn("Unable to delete the course with id: " + courseId);
        return result;
    }

    @Override
    public boolean deleteTopicFromCourse(int courseId, int topicId) {
        boolean result = courseDao.deleteTopicFromCourse(courseId, topicId);
        if (result)
            LOG.info("Topic has been deleted from course with id: " + courseId);
        else
            LOG.warn("Unable to delete a topic from a course with id: " + courseId);
        return result;
    }

    @Override
    public boolean addStudent(int courseId, int userId) {
        boolean result = courseDao.addStudent(courseId, userId);
        if (result)
            LOG.info("Student has been added to course with id: " + courseId);
        else
            LOG.warn("Unable to add student to the course with id: " + courseId);
        return result;
    }

    @Override
    public boolean leaveCourse(int courseId, int userId) {
        boolean result = courseDao.leaveCourse(courseId, userId);
        if (result)
            LOG.info("Student left the course with id: " + courseId);
        else
            LOG.warn("Unable to leave the course with id: " + courseId);
        return result;
    }

    @Override
    public boolean addTopics(CourseDto courseDto) {
        boolean result = courseDao.addTopics(courseDto);
        if (result)
            LOG.info("Topics has been added to course with id: " + courseDto.getId());
        else
            LOG.warn("Unable to add topics to the course with id: " + courseDto.getId());
        return result;
    }

    @Override
    public List<CourseDto> getAll() {
        List<CourseDto> result = courseDao.getAll();
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByUserId(int userId) {
        List<CourseDto> result = courseDao.getAllByUserId(userId);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByStatus(String status) {
        List<CourseDto> result = courseDao.getAllByStatus(status);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByAlphabet(String alphabet) {
        List<CourseDto> result = courseDao.getAllByAlphabet(alphabet);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByDuration(String duration) {
        List<CourseDto> result = courseDao.getAllByDuration(duration);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByName(String name) {
        List<CourseDto> result = courseDao.getAllByName(name);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByTopic(String topicName) {
        List<CourseDto> result = courseDao.getAllByTopic(topicName);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByDateStart(Date dateStart) {
        List<CourseDto> result = courseDao.getAllByDateStart(dateStart);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<CourseDto> getAllByStatusUserId(String status, int userId) {
        List<CourseDto> result = courseDao.getAllByStatusUserId(status, userId);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public List<UserDto> getAllStudentsWithGradesByCourseId(int courseId) {
        List<UserDto> result = courseDao.getAllStudentsWithGradesByCourseId(courseId);
        LOG.info(result.size() + " courses were found");
        return result;
    }

    @Override
    public CourseDto getCourseById(int id) {
        CourseDto result = courseDao.getCourseById(id);
        if (result != null)
            LOG.info("Course: " + result.getName() + " were found");
        else
            LOG.info("No course found by id: " + id);
        return result;
    }
}