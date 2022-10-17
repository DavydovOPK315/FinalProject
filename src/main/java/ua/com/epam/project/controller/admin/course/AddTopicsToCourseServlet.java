package ua.com.epam.project.controller.admin.course;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to add topics to course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/add/topics")
public class AddTopicsToCourseServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int courseId = Integer.parseInt(req.getParameter("id"));
        String[] topics = req.getParameterValues("selected_topics");
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setTopics(topics);

        if (topics.length > 0) {
            boolean result = courseService.addTopics(courseDto);

            if (result)
                session.setAttribute("message", "admin.courses.edit.course.add.topic");
            else
                session.setAttribute("message", "admin.courses.edit.course.add.topic.unable");
        } else
            session.setAttribute("message", "admin.courses.edit.course.add.topic.unable");
        resp.sendRedirect(req.getContextPath() + "/admin/courses/edit?id=" + courseId);
    }
}
