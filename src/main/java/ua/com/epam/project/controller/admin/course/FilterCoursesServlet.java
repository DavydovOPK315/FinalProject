package ua.com.epam.project.controller.admin.course;

import lombok.SneakyThrows;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet to filter courses
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/filter")
public class FilterCoursesServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();
    private final UserService userService = ServiceFactory.getUserService();
    private final TopicService topicService = ServiceFactory.getTopicService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String key = req.getParameter("key");
        String value = req.getParameter("value");
        List<CourseDto> courseDtoList = new ArrayList<>();
        List<UserDto> teachers = userService.getAllByRole("TEACHER");
        List<Topic> topics = topicService.getAll();

        req.setAttribute("teachers", teachers);
        req.setAttribute("topics", topics);

        if (key.equals("date_started")) {
            Date dateStart = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(value).getTime());
            courseDtoList = courseService.getAllByDateStart(dateStart);
            req.setAttribute("message", "elective.courses.filter.start.date");
            req.setAttribute("messageValue", dateStart);
        } else if (value != null && !value.trim().isEmpty())
            switch (key) {
                case "status":
                    courseDtoList = courseService.getAllByStatus(value);
                    req.setAttribute("message", "elective.courses.filter.status");
                    req.setAttribute("messageValue", value);
                    break;
                case "alphabet":
                    courseDtoList = courseService.getAllByAlphabet(value);
                    req.setAttribute("message", "elective.courses.filter.name");
                    req.setAttribute("messageValue", value);
                    break;
                case "duration":
                    courseDtoList = courseService.getAllByDuration(value);
                    req.setAttribute("message", "elective.courses.filter.duration");
                    req.setAttribute("messageValue", value);
                    break;
                case "students":
                    if (value.equals("MIN-MAX"))
                        courseDtoList = courseService.getAll().stream().sorted(Comparator.comparingInt(CourseDto::getNumberStudents)).collect(Collectors.toList());
                    else
                        courseDtoList = courseService.getAll().stream().sorted((o1, o2) -> o2.getNumberStudents() - o1.getNumberStudents()).collect(Collectors.toList());
                    req.setAttribute("message", "elective.courses.filter.students");
                    req.setAttribute("messageValue", value);
                    break;
                case "teacher_login":
                    courseDtoList = courseService.getAllByUserId(Integer.parseInt(value));
                    req.setAttribute("message", "elective.courses.filter.teacher.login");
                    req.setAttribute("messageValue", userService.getUserById(Integer.parseInt(value)).getLogin());
                    break;
                case "name":
                    courseDtoList = courseService.getAllByName(value);
                    req.setAttribute("message", "elective.courses.filter.name");
                    req.setAttribute("messageValue", value);
                    break;
                case "login":
                    UserDto userDto = userService.getUserByLogin(value);
                    if (userDto != null)
                        courseDtoList = courseService.getAllByUserId(userDto.getId());
                    req.setAttribute("message", "elective.courses.filter.login");
                    req.setAttribute("messageValue", value);
                    break;
                case "topic":
                    courseDtoList = courseService.getAllByTopic(value);
                    req.setAttribute("message", "elective.courses.filter.topic");
                    req.setAttribute("messageValue", value);
                    break;
            }

        req.setAttribute("dateNow", new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        req.setAttribute("listSize", courseDtoList.size());
        User currentUser = (User) session.getAttribute("user");

        if (currentUser.getRoleId() == 1) {
            req.setAttribute("courseList", courseDtoList);
            getServletContext().getRequestDispatcher("/courses.jsp").forward(req, resp);
        } else {
            int recordsPerPage = 6;
            int page = 1;
            int noOfRecords = courseDtoList.size();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            if (req.getParameter("page") != null)
                page = Integer.parseInt(req.getParameter("page"));

            int fromIndex = (page - 1) * recordsPerPage;
            int endIndex = Math.min(fromIndex + recordsPerPage, noOfRecords);

            List<CourseDto> resultList = new ArrayList<>();

            if (!courseDtoList.isEmpty())
                resultList = courseDtoList.subList(fromIndex, endIndex);

            String servletPath = req.getRequestURI().substring(req.getContextPath().length());

            req.setAttribute("servletPath", servletPath);
            req.setAttribute("noOfPages", noOfPages);
            req.setAttribute("currentPage", page);
            req.setAttribute("courseList", resultList);
            req.setAttribute("key", key);
            req.setAttribute("value", value);

            getServletContext().getRequestDispatcher("/elective_courses.jsp").forward(req, resp);
        }
    }
}