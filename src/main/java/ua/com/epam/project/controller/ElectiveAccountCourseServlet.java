package ua.com.epam.project.controller;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display course with all info
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/course")
public class ElectiveAccountCourseServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();
    private final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setIntHeader("Refresh", 1800);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int courseId = Integer.parseInt(req.getParameter("id"));
        CourseDto course = courseService.getCourseById(courseId);
        List<UserDto> students = courseService.getAllStudentsWithGradesByCourseId(courseId);
        UserDto userResult = students.stream().filter(userDto -> userDto.getId() == user.getId()).findFirst().orElse(null);
        List<Topic> topics = topicService.getAllByCourseId(courseId);

        if (userResult == null && !course.getTeacherLogin().equals(user.getLogin())) {
            resp.sendError(500);
        } else {
            req.setAttribute("course", course);
            req.setAttribute("user", userResult);
            req.setAttribute("topics", topics);
            req.setAttribute("students", students);
            getServletContext().getRequestDispatcher("/elective_account_course.jsp").forward(req, resp);
        }
    }
}