package ua.com.epam.project.service;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;

import java.sql.Date;
import java.util.List;

/**
 * Course service
 *
 * @author Denis Davydov
 * @version 2.0
 */
public interface CourseService {

    /**
     * Function to create course
     *
     * @param courseDto course object
     * @return return query result can be true or false
     */
    boolean createCourse(CourseDto courseDto);

    /**
     * Function to update course by course object
     *
     * @param course course object
     * @return return query result can be true or false
     */
    boolean updateCourse(CourseDto course);

    /**
     * Function to update course grades
     *
     * @param courseId   course ID
     * @param studentsId array of students IDs
     * @param grades     array of grades
     * @return return query result can be true or false
     */
    boolean updateGrades(int courseId, String[] studentsId, String[] grades);

    /**
     * Function to add topics to course
     *
     * @param courseDto course object
     * @return return query result can be true or false
     */
    boolean addTopics(CourseDto courseDto);

    /**
     * Function to add a student to the course
     *
     * @param courseId course ID
     * @param userId   user ID
     * @return return query result can be true or false
     */
    boolean addStudent(int courseId, int userId);

    /**
     * Function to delete course
     *
     * @param courseId course ID
     * @return return query result can be true or false
     */
    boolean deleteCourse(int courseId);

    /**
     * Function to delete topic from course
     *
     * @param courseId course ID
     * @param topicId  topic ID
     * @return return query result can be true or false
     */
    boolean deleteTopicFromCourse(int courseId, int topicId);

    /**
     * Function to leave the course
     *
     * @param courseId course ID
     * @param userId   user ID
     * @return return query result can be true or false
     */
    boolean leaveCourse(int courseId, int userId);

    /**
     * Function to get course by ID
     *
     * @param id course ID
     * @return return course by ID
     */
    CourseDto getCourseById(int id);

    /**
     * Function to get all courses
     *
     * @return return all courses
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAll();

    /**
     * Function to get all courses by course ID
     *
     * @param courseId course ID
     * @return return all courses by course ID
     * @see #getAll()
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<UserDto> getAllStudentsWithGradesByCourseId(int courseId);

    /**
     * Function to get all courses by user ID
     *
     * @param userId user ID
     * @return return all courses by user ID
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAll()
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByUserId(int userId);

    /**
     * Function to get all courses by status
     *
     * @param status course status
     * @return return all courses by status
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAll()
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByStatus(String status);

    /**
     * Function to get all courses by alphabet
     *
     * @param alphabet alphabet value (az/za)
     * @return return all courses by alphabet
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAll()
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByAlphabet(String alphabet);

    /**
     * Function to get all courses by duration
     *
     * @param duration duration (min-max/max-min)
     * @return return all courses by duration
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAll()
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByDuration(String duration);

    /**
     * Function to get all courses by name
     *
     * @param name name
     * @return return all courses by name
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAll()
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByName(String name);

    /**
     * Function to get all courses by topic name
     *
     * @param topicName topic name
     * @return return all courses by topic name
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAll()
     * @see #getAllByDateStart(Date)
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByTopic(String topicName);

    /**
     * Function to get all courses by date started
     *
     * @param dateStart date started
     * @return return all courses by date started
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAll()
     * @see #getAllByStatusUserId(String, int)
     */
    List<CourseDto> getAllByDateStart(Date dateStart);

    /**
     * Function to get all courses by status and user ID
     *
     * @param status status
     * @param userId user ID
     * @return return all courses by status and user ID
     * @see #getAllStudentsWithGradesByCourseId(int)
     * @see #getAllByUserId(int)
     * @see #getAllByStatus(String)
     * @see #getAllByAlphabet(String)
     * @see #getAllByDuration(String)
     * @see #getAllByName(String)
     * @see #getAllByTopic(String)
     * @see #getAllByDateStart(Date)
     * @see #getAll()
     */
    List<CourseDto> getAllByStatusUserId(String status, int userId);
}