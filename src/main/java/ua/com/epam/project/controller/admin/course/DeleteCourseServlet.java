package ua.com.epam.project.controller.admin.course;

import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to delete course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/courses/delete")
public class DeleteCourseServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int courseId = Integer.parseInt(req.getParameter("id"));
        boolean result = courseService.deleteCourse(courseId);

        if (result) {
            session.setAttribute("message", "admin.courses.course.deleted");
            resp.sendRedirect(req.getContextPath() + "/admin/courses");
        } else {
            session.setAttribute("message", "admin.courses.course.delete.unable");
            resp.sendRedirect(req.getContextPath() + "/admin/courses/edit?id=" + courseId);
        }
    }
}