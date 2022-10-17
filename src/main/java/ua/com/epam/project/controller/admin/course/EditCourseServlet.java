package ua.com.epam.project.controller.admin.course;

import lombok.SneakyThrows;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet to edit course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/edit")
public class EditCourseServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();
    private final UserService userService = ServiceFactory.getUserService();
    private final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int courseId = Integer.parseInt(req.getParameter("id"));
        CourseDto course = courseService.getCourseById(courseId);
        List<UserDto> users = userService.getAllByRole("TEACHER");
        List<UserDto> students = courseService.getAllStudentsWithGradesByCourseId(courseId);
        List<Topic> topics = topicService.getAllByCourseId(courseId);
        List<Topic> allTopics = topicService.getAll();
        List<Topic> remainingTopics = allTopics.stream().filter(topic -> !topics.contains(topic)).collect(Collectors.toList());

        req.setAttribute("course", course);
        req.setAttribute("users", users);
        req.setAttribute("students", students);
        req.setAttribute("topics", topics);
        req.setAttribute("allTopics", remainingTopics);
        getServletContext().getRequestDispatcher("/course_edit.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int courseId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date_start"));
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date_end"));
        String description = req.getParameter("description").trim();
        String teacherLogin = req.getParameter("teacher_login").trim();

        if (name.length() < 3 || description.length() < 5 || teacherLogin.isEmpty() || dateStart.after(dateEnd) || dateStart == dateEnd)
            session.setAttribute("message", "registration.invalid.data");
        else {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(courseId);
            courseDto.setName(name.trim());
            courseDto.setDateStart(dateStart);
            courseDto.setDateEnd(dateEnd);
            courseDto.setDescription(description.trim());
            courseDto.setTeacherLogin(teacherLogin);

            boolean result = courseService.updateCourse(courseDto);
            if (result)
                session.setAttribute("message", "admin.courses.edit.course.updated");
            else
                session.setAttribute("message", "admin.courses.edit.course.unable");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/courses/edit?id=" + courseId);
    }
}