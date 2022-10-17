package ua.com.epam.project.controller.admin.course;

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
 * Servlet to delete topic from course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/topics/delete")
public class DeleteTopicFromCourseServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        int topicId = Integer.parseInt(req.getParameter("topicId"));
        boolean result = courseService.deleteTopicFromCourse(courseId, topicId);

        if (result)
            session.setAttribute("message", "admin.courses.edit.course.delete.topic");
        else
            session.setAttribute("message", "admin.courses.edit.course.delete.topic.unable");
        resp.sendRedirect(req.getContextPath() + "/admin/courses/edit?id=" + courseId);
    }
}