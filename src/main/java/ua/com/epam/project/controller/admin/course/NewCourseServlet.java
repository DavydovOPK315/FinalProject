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

/**
 * Servlet to create new course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/new")
public class NewCourseServlet extends HttpServlet {
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

        List<UserDto> users = userService.getAllByRole("TEACHER");
        List<Topic> topics = topicService.getAll();

        req.setAttribute("users", users);
        req.setAttribute("topics", topics);
        req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        getServletContext().getRequestDispatcher("/course_new.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String name = req.getParameter("name");
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date_start"));
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date_end"));
        String description = req.getParameter("description");
        String teacherLogin = req.getParameter("teacher_login");
        String[] topics = req.getParameterValues("selected_topics");

        if (name.length() < 3 || description.length() < 5 || teacherLogin.isEmpty() || dateStart.after(dateEnd) || dateStart.equals(dateEnd) || topics.length < 1) {
            session.setAttribute("message", "registration.invalid.data");
            resp.sendRedirect(req.getContextPath() + "/admin/courses/new");
        } else {
            CourseDto courseDto = new CourseDto();
            courseDto.setName(name.trim());
            courseDto.setDateStart(dateStart);
            courseDto.setDateEnd(dateEnd);
            courseDto.setDescription(description.trim());
            courseDto.setTeacherLogin(teacherLogin);
            courseDto.setTopics(topics);

            boolean result = courseService.createCourse(courseDto);
            if (result) {
                session.setAttribute("message", "admin.courses.new.message.success");
                int id = courseService.getAllByName(name).stream().findFirst().filter(course -> course.getName().equals(name.trim())).orElse(new CourseDto()).getId();
                resp.sendRedirect(req.getContextPath() + "/admin/courses/edit?id=" + id);
            } else {
                session.setAttribute("message", "admin.courses.new.message.unable");
                resp.sendRedirect(req.getContextPath() + "/admin/courses/new");
            }
        }
    }
}