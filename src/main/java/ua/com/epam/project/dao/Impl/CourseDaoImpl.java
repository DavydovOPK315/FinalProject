package ua.com.epam.project.dao.Impl;

import org.apache.log4j.Logger;
import ua.com.epam.project.dao.ConnectionPool;
import ua.com.epam.project.dao.CourseDao;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.Performance;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.service.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Course DAO implementation
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class CourseDaoImpl implements CourseDao {
    private static final String INSERT_INTO_COURSES = "INSERT INTO courses (name, date_start, date_end, description) values (?, ?, ?, ?);";
    private static final String SELECT_COURSES_WITH_TEACHER = "SELECT c.*, u.id, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER') ORDER BY c.id;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_ALPHABET = "SELECT c.*, u.id, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER') ORDER BY c.name;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_ALPHABET_DESC = "SELECT c.*, u.id, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER') ORDER BY c.name DESC;";
    private static final String SELECT_COURSES_WITH_TEACHER_DURATION = "SELECT c.*, u.id, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER') ORDER BY DATEDIFF(c.date_end, c.date_start);";
    private static final String SELECT_COURSES_WITH_TEACHER_DURATION_DESC = "SELECT c.*, u.id, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER') ORDER BY DATEDIFF(c.date_end, c.date_start) DESC;";
    private static final String SELECT_COURSE_WITH_STUDENTS = "SELECT u.id, u.login, u.first_name, u.last_name FROM courses c JOIN users_has_courses us ON c.id = ? AND c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.status = 'ACTIVE' AND u.role_id = (SELECT id FROM roles WHERE name = 'STUDENT') ORDER BY c.id;";
    private static final String SELECT_COURSE_BY_ID = "SELECT c.*, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id AND c.id = ? JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER');";
    private static final String UPDATE_COURSE_BY_ID = "UPDATE courses c SET c.name = ?, c.date_start = ?, c.date_end = ?, c.description = ? WHERE c.id = ?;";
    private static final String INSERT_INTO_USERS_HAS_COURSES = "INSERT INTO users_has_courses (users_id, courses_id) values (?, ?);";
    private static final String INSERT_INTO_COURSES_HAS_TOPICS = "INSERT INTO courses_has_topics (courses_id, topics_id) values (?, ?);";
    private static final String INSERT_INTO_PERFORMANCE = "INSERT INTO performance (grade, users_id, topics_id, courses_id) values (?, ?, ?, ?);";
    private static final String UPDATE_PERFORMANCE = "UPDATE performance SET grade = ? WHERE users_id = ? AND topics_id = ? AND courses_id = ?;";
    private static final String UPDATE_USERS_HAS_COURSES = "UPDATE users_has_courses SET users_id = ? WHERE users_id = ? AND courses_id = ?";
    private static final String SELECT_STUDENTS_WITH_GRADES = "SELECT p.id, p.grade, t.id, t.name FROM performance p, topics t WHERE p.users_id = ? AND p.courses_id = ? AND p.topics_id = t.id;";
    private static final String DELETE_FROM_COURSES = "DELETE FROM courses WHERE id = ?;";
    private static final String DELETE_FROM_PERFORMANCE_TOPIC = "DELETE FROM performance WHERE courses_id = ? AND topics_id = ?";
    private static final String DELETE_FROM_PERFORMANCE_COURSE = "DELETE FROM performance WHERE courses_id = ?;";
    private static final String DELETE_FROM_PERFORMANCE_USER = "DELETE FROM performance WHERE users_id = ? AND courses_id = ?";
    private static final String DELETE_FROM_COURSES_HAS_TOPICS_TOPIC = "DELETE FROM courses_has_topics WHERE courses_id = ? AND topics_id = ?";
    private static final String DELETE_FROM_COURSES_HAS_TOPICS_COURSE = "DELETE FROM courses_has_topics WHERE courses_id = ?";
    private static final String DELETE_FROM_USERS_HAS_COURSES_USER = "DELETE FROM users_has_courses WHERE users_id = ? AND courses_id = ?";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_USER = "SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id AND us.users_id = ? ORDER BY t.status;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_STATUS = "SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id AND c.status = ? JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id GROUP BY t.id;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_STATUS_AND_USER_ID = "SELECT * FROM (SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id AND c.status = ? JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id GROUP BY t.id) r, users_has_courses l WHERE r.id = l.courses_id AND l.users_id = ?;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_DATE_START = "SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id AND c.date_start >= ? JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id GROUP BY t.id ORDER BY t.date_start;";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_NAME = "SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id AND c.name LIKE ? JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id GROUP BY t.id;";
    private static final String CALL_STORED_PROCEDURE_CHECKING_STATUS = "{call checking_status()}";
    private static final String SELECT_COURSES_WITH_TEACHER_BY_TOPIC = "SELECT t2.* FROM (SELECT t.* FROM (SELECT c.*, u.id as userId, u.login FROM courses c JOIN users_has_courses us ON c.id = us.courses_id JOIN users u ON u.id = us.users_id AND u.role_id = (SELECT id FROM roles WHERE name = 'TEACHER')) t, users_has_courses us WHERE t.id = us.courses_id GROUP BY t.id) t2, courses_has_topics ct WHERE t2.id = ct.courses_id AND ct.topics_id = ?;";
    private static CourseDaoImpl instance;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private UserService userService = ServiceFactory.getUserService();
    private TopicService topicService = ServiceFactory.getTopicService();
    private static final Logger LOG = Logger.getLogger(CourseDaoImpl.class);

    private CourseDaoImpl() {
    }

    public static synchronized CourseDaoImpl getInstance() {
        if (instance == null) instance = new CourseDaoImpl();
        return instance;
    }

    @Override
    public boolean createCourse(CourseDto courseDto) {
        Connection con = connectionPool.getConnection();
        int courseId;

        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_COURSES, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            int k = 1;
            ps.setString(k++, courseDto.getName());
            ps.setDate(k++, new Date(courseDto.getDateStart().getTime()));
            ps.setDate(k++, new Date(courseDto.getDateEnd().getTime()));
            ps.setString(k, courseDto.getDescription());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys();
                 PreparedStatement ps2 = con.prepareStatement(INSERT_INTO_USERS_HAS_COURSES)) {

                if (!rs.next())
                    throw new SQLException();

                int userId = userService.getUserByLogin(courseDto.getTeacherLogin()).getId();
                courseId = rs.getInt(1);
                ps2.setInt(1, userId);
                ps2.setInt(2, courseId);
                ps2.executeUpdate();
            }

            try (PreparedStatement ps3 = con.prepareStatement(INSERT_INTO_COURSES_HAS_TOPICS)) {
                for (int i = 0; i < courseDto.getTopics().length; i++) {
                    int topicId = Integer.parseInt(courseDto.getTopics()[i]);
                    ps3.setInt(1, courseId);
                    ps3.setInt(2, topicId);
                    ps3.executeUpdate();
                }
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error("createCourse: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
        return true;
    }

    private void callStoredProcedureCheckingStatus(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.executeQuery(CALL_STORED_PROCEDURE_CHECKING_STATUS);
        }
    }

    @Override
    public List<CourseDto> getAll() {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            return getListOfCourseDto(result, SELECT_COURSES_WITH_TEACHER, con, statement);
        } catch (SQLException e) {
            LOG.error("getAll: sql exception");
            return result;
        }
    }

    @Override
    public List<UserDto> getAllStudentsWithGradesByCourseId(int courseId) {
        List<UserDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSE_WITH_STUDENTS)) {
            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserDto userDto = new UserDto();
                    int k = 1;
                    userDto.setId(rs.getInt(k++));
                    userDto.setLogin(rs.getString(k++));
                    userDto.setFirstName(rs.getString(k++));
                    userDto.setLastName(rs.getString(k));

                    try (PreparedStatement ps2 = con.prepareStatement(SELECT_STUDENTS_WITH_GRADES)) {
                        ps2.setInt(1, userDto.getId());
                        ps2.setInt(2, courseId);

                        try (ResultSet rs2 = ps2.executeQuery()) {
                            while (rs2.next()) {
                                Performance performance = new Performance();
                                k = 1;
                                performance.setPerformanceId(rs2.getInt(k++));
                                performance.setGrade(rs2.getInt(k++));
                                performance.setTopicId(rs2.getInt(k++));
                                performance.setTopicName(rs2.getString(k));
                                userDto.getPerformanceList().add(performance);
                            }
                        }
                    }
                    result.add(userDto);
                }
                return result;
            }
        } catch (SQLException e) {
            LOG.error("getAllStudentsWithGradesByCourseId: sql exception");
            return result;
        }
    }

    @Override
    public CourseDto getCourseById(int id) {
        CourseDto courseDto = null;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSE_BY_ID)) {
            callStoredProcedureCheckingStatus(con);
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int k = 1;
                    courseDto = new CourseDto();
                    courseDto.setId(rs.getInt(k++));
                    courseDto.setName(rs.getString(k++));
                    courseDto.setDateStart(rs.getDate(k++));
                    courseDto.setDateEnd(rs.getDate(k++));
                    courseDto.setDescription(rs.getString(k++));
                    courseDto.setCreated(rs.getDate(k++));
                    courseDto.setStatus(rs.getString(k++));
                    courseDto.setTeacherLogin(rs.getString(k));

                    List<UserDto> usersOnCourse = getAllStudentsWithGradesByCourseId(courseDto.getId());
                    courseDto.setNumberStudents(usersOnCourse.size());
                }
                return courseDto;
            }
        } catch (SQLException e) {
            LOG.error("getCourseById: sql exception");
            return courseDto;
        }
    }

    @Override
    public boolean addTopics(CourseDto courseDto) {
        Connection con = connectionPool.getConnection();

        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_PERFORMANCE)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            int courseId = courseDto.getId();
            String[] topicsId = courseDto.getTopics();
            List<UserDto> users = getAllStudentsWithGradesByCourseId(courseId);

            try (PreparedStatement ps2 = con.prepareStatement(INSERT_INTO_COURSES_HAS_TOPICS)) {
                for (String s : topicsId) {
                    int topicId = Integer.parseInt(s);
                    ps2.setInt(1, courseId);
                    ps2.setInt(2, topicId);
                    ps2.executeUpdate();
                }
            }

            int k;
            for (String s : topicsId) {
                for (UserDto user : users) {
                    k = 1;
                    ps.setInt(k++, 0);
                    ps.setInt(k++, user.getId());
                    ps.setInt(k++, Integer.parseInt(s));
                    ps.setInt(k, courseId);
                    ps.executeUpdate();
                }
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            LOG.error("addTopics: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
    }

    @Override
    public boolean addStudent(int courseId, int userId) {
        List<Topic> topics = topicService.getAllByCourseId(courseId);
        Connection con = connectionPool.getConnection();

        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_PERFORMANCE)) {
            con.setAutoCommit(false);
            try (PreparedStatement ps2 = con.prepareStatement(INSERT_INTO_USERS_HAS_COURSES)) {
                ps2.setInt(1, userId);
                ps2.setInt(2, courseId);
                ps2.executeUpdate();
            }

            int k;
            for (Topic topic : topics) {
                k = 1;
                ps.setInt(k++, 0);
                ps.setInt(k++, userId);
                ps.setInt(k++, topic.getId());
                ps.setInt(k, courseId);
                ps.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            LOG.error("addStudent: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
    }

    @Override
    public boolean updateCourse(CourseDto course) {
        int userIdOld = userService.getUserByLogin(getCourseById(course.getId()).getTeacherLogin()).getId();
        int userIdNew = userService.getUserByLogin(course.getTeacherLogin()).getId();
        int courseId = course.getId();
        Connection con = connectionPool.getConnection();

        try (PreparedStatement ps = con.prepareStatement(UPDATE_COURSE_BY_ID)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            int k = 1;
            ps.setString(k++, course.getName());
            ps.setDate(k++, new Date(course.getDateStart().getTime()));
            ps.setDate(k++, new Date(course.getDateEnd().getTime()));
            ps.setString(k++, course.getDescription());
            ps.setInt(k, course.getId());
            ps.executeUpdate();

            try (PreparedStatement ps2 = con.prepareStatement(UPDATE_USERS_HAS_COURSES)) {
                ps2.setInt(1, userIdNew);
                ps2.setInt(2, userIdOld);
                ps2.setInt(3, courseId);
                ps2.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error("updateCourse: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
        return true;
    }

    @Override
    public boolean updateGrades(int courseId, String[] studentsId, String[] grades) {
        List<Topic> topics = topicService.getAllByCourseId(courseId);
        List<UserDto> students = getAllStudentsWithGradesByCourseId(courseId);
        Connection con = connectionPool.getConnection();

        try (PreparedStatement ps = con.prepareStatement(UPDATE_PERFORMANCE)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            int numberStudents = studentsId.length;
            int numberGrades = grades.length;
            int numberRepeats = numberGrades / numberStudents;
            int k = 0;

            for (int i = 0; i < numberStudents; i++) {
                for (int j = 0; j < numberRepeats; j++) {
                    int grade = Integer.parseInt(grades[k]);
                    int userId = students.get(i).getId();
                    int topicId = topics.get(j).getId();
                    k++;
                    ps.setInt(1, grade);
                    ps.setInt(2, userId);
                    ps.setInt(3, topicId);
                    ps.setInt(4, courseId);
                    ps.executeUpdate();
                }
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            LOG.error("updateGrades: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
    }

    @Override
    public boolean deleteTopicFromCourse(int courseId, int topicId) {
        Connection con = connectionPool.getConnection();
        boolean result;

        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_PERFORMANCE_TOPIC)) {
            result = deleteCommon(courseId, topicId, con, ps, DELETE_FROM_COURSES_HAS_TOPICS_TOPIC);
        } catch (SQLException e) {
            LOG.error("deleteTopicFromCourse: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
        return result;
    }

    private boolean deleteCommon(int courseId, int topicId, Connection con, PreparedStatement ps, String deleteFromCoursesHasTopicsTopic) throws SQLException {
        con.setAutoCommit(false);
        con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        ps.setInt(1, courseId);
        ps.setInt(2, topicId);
        ps.executeUpdate();

        try (PreparedStatement ps2 = con.prepareStatement(deleteFromCoursesHasTopicsTopic)) {
            ps2.setInt(1, courseId);
            ps2.setInt(2, topicId);
            ps2.executeUpdate();
        }
        con.commit();
        return true;
    }

    @Override
    public boolean leaveCourse(int courseId, int userId) {
        Connection con = connectionPool.getConnection();
        boolean result;

        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_PERFORMANCE_USER)) {
            result = deleteCommon(userId, courseId, con, ps, DELETE_FROM_USERS_HAS_COURSES_USER);
        } catch (SQLException e) {
            LOG.error("leaveCourse: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
        return result;
    }

    @Override
    public boolean deleteCourse(int courseId) {
        Connection con = connectionPool.getConnection();

        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_PERFORMANCE_COURSE)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ps.setInt(1, courseId);
            ps.executeUpdate();

            try (PreparedStatement ps2 = con.prepareStatement(DELETE_FROM_COURSES_HAS_TOPICS_COURSE)) {
                ps2.setInt(1, courseId);
                ps2.executeUpdate();
            }
            try (PreparedStatement ps3 = con.prepareStatement(DELETE_FROM_COURSES)) {
                ps3.setInt(1, courseId);
                ps3.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            LOG.error("deleteCourse: sql exception");
            connectionPool.rollback(con);
            connectionPool.close(con);
            return false;
        } finally {
            connectionPool.close(con);
        }
        return true;
    }

    @Override
    public List<CourseDto> getAllByUserId(int userId) {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_USER)) {
            callStoredProcedureCheckingStatus(con);
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                CourseDto course;

                while (rs.next()) {
                    course = getCourseDto(rs);

                    try (PreparedStatement ps2 = con.prepareStatement("SELECT ROUND(AVG(grade),1) FROM performance WHERE users_id = ? AND courses_id = ?;")) {
                        ps2.setInt(1, userId);
                        ps2.setInt(2, course.getId());

                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next())
                                course.setAverageGrade(rs2.getInt(1));
                        }
                    }
                    result.add(course);
                }
                return result;
            }
        } catch (SQLException e) {
            LOG.error("getAllByUserId: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByStatus(String value) {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_STATUS)) {
            callStoredProcedureCheckingStatus(con);
            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                return setListOfCourseDto(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByStatus: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByStatusUserId(String value, int userId) {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_STATUS_AND_USER_ID)) {
            callStoredProcedureCheckingStatus(con);
            ps.setString(1, value);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return setListOfCourseDto(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByStatusUserId: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByName(String value) {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_NAME)) {
            callStoredProcedureCheckingStatus(con);
            value = "%" + value + "%";
            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                return setListOfCourseDto(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByName: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByTopic(String value) {
        int topicId = topicService.getTopicByName(value).getId();
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_TOPIC)) {
            callStoredProcedureCheckingStatus(con);
            ps.setInt(1, topicId);

            try (ResultSet rs = ps.executeQuery()) {
                return setListOfCourseDto(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByTopic: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByDateStart(Date dateStart) {
        List<CourseDto> result = new ArrayList<>();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_COURSES_WITH_TEACHER_BY_DATE_START)) {
            callStoredProcedureCheckingStatus(con);
            ps.setDate(1, dateStart);

            try (ResultSet rs = ps.executeQuery()) {
                return setListOfCourseDto(result, rs);
            }
        } catch (SQLException e) {
            LOG.error("getAllByDateStart: sql exception");
            return result;
        }
    }

    @Override
    public List<CourseDto> getAllByAlphabet(String value) {
        List<CourseDto> result = new ArrayList<>();
        String query;

        if (value.equals("AZ"))
            query = SELECT_COURSES_WITH_TEACHER_BY_ALPHABET;
        else
            query = SELECT_COURSES_WITH_TEACHER_BY_ALPHABET_DESC;

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            return getListOfCourseDto(result, query, con, statement);
        } catch (SQLException e) {
            LOG.error("getAllByAlphabet: sql exception");
            return result;
        }
    }

    private List<CourseDto> getListOfCourseDto(List<CourseDto> result, String query, Connection con, Statement statement) throws SQLException {
        callStoredProcedureCheckingStatus(con);

        try (ResultSet rs = statement.executeQuery(query)) {
            return setListOfCourseDto(result, rs);
        }
    }

    private List<CourseDto> setListOfCourseDto(List<CourseDto> result, ResultSet rs) throws SQLException {
        CourseDto course;
        while (rs.next()) {
            course = getCourseDto(rs);
            result.add(course);
        }
        return result;
    }

    private CourseDto getCourseDto(ResultSet rs) throws SQLException {
        CourseDto course;
        course = new CourseDto();
        int k = 1;
        course.setId(rs.getInt(k++));
        course.setName(rs.getString(k++));
        course.setDateStart(rs.getDate(k++));
        course.setDateEnd(rs.getDate(k++));
        course.setDescription(rs.getString(k++));
        course.setCreated(rs.getDate(k++));
        course.setStatus(rs.getString(k++));
        course.setTeacherId(rs.getInt(k++));
        course.setTeacherLogin(rs.getString(k));

        List<UserDto> usersOnCourse = getAllStudentsWithGradesByCourseId(course.getId());
        course.setNumberStudents(usersOnCourse.size());
        return course;
    }

    @Override
    public List<CourseDto> getAllByDuration(String value) {
        List<CourseDto> result = new ArrayList<>();
        String query;

        if (value.equals("MIN-MAX"))
            query = SELECT_COURSES_WITH_TEACHER_DURATION;
        else
            query = SELECT_COURSES_WITH_TEACHER_DURATION_DESC;

        try (Connection con = connectionPool.getConnection();
             Statement statement = con.createStatement()) {
            return getListOfCourseDto(result, query, con, statement);
        } catch (SQLException e) {
            LOG.error("getAllByDuration: sql exception");
            return result;
        }
    }
}