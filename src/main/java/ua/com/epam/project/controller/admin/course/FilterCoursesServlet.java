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
            Date dateStart = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date_start")).getTime());
            courseDtoList = courseService.getAllByDateStart(dateStart);
            req.setAttribute("message","elective.courses.filter.start.date");
            req.setAttribute("value", dateStart);
        } else if (value != null && !value.trim().isEmpty())
            switch (key) {
                case "status":
                    courseDtoList = courseService.getAllByStatus(value);
                    req.setAttribute("message","elective.courses.filter.status");
                    req.setAttribute("value",value);
                    break;
                case "alphabet":
                    courseDtoList = courseService.getAllByAlphabet(value);
                    req.setAttribute("message","elective.courses.filter.name");
                    req.setAttribute("value",value);
                    break;
                case "duration":
                    courseDtoList = courseService.getAllByDuration(value);
                    req.setAttribute("message","elective.courses.filter.duration");
                    req.setAttribute("value",value);
                    break;
                case "students":
                    if (value.equals("MIN-MAX"))
                        courseDtoList = courseService.getAll().stream().sorted(Comparator.comparingInt(CourseDto::getNumberStudents)).collect(Collectors.toList());
                    else
                        courseDtoList = courseService.getAll().stream().sorted((o1, o2) -> o2.getNumberStudents() - o1.getNumberStudents()).collect(Collectors.toList());
                    req.setAttribute("message","elective.courses.filter.students");
                    req.setAttribute("value",value);
                    break;
                case "teacher_login":
                    courseDtoList = courseService.getAllByUserId(Integer.parseInt(value));
                    req.setAttribute("message","elective.courses.filter.teacher.login");
                    req.setAttribute("value", userService.getUserById(Integer.parseInt(value)).getLogin());
                    break;
                case "name":
                    courseDtoList = courseService.getAllByName(value);
                    req.setAttribute("message","elective.courses.filter.name");
                    req.setAttribute("value",value);
                    break;
                case "login":
                    UserDto userDto = userService.getUserByLogin(value);
                    courseDtoList = courseService.getAllByUserId(userDto.getId());
                    req.setAttribute("message","elective.courses.filter.login");
                    req.setAttribute("value",value);
                    break;
                case "topic":
                    courseDtoList = courseService.getAllByTopic(value);
                    req.setAttribute("message","elective.courses.filter.topic");
                    req.setAttribute("value",value);
                    break;
            }

        req.setAttribute("courseList", courseDtoList);
        req.setAttribute("dateNow", new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        User currentUser = (User) session.getAttribute("user");
        if (currentUser.getRoleId() == 1)
            getServletContext().getRequestDispatcher("/courses.jsp").forward(req, resp);
        else
            getServletContext().getRequestDispatcher("/elective_courses.jsp").forward(req, resp);
    }
}