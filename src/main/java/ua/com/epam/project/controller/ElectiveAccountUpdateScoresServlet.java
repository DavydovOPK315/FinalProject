package ua.com.epam.project.controller;

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
 * Servlet for updating course grades
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/update/scores")
public class ElectiveAccountUpdateScoresServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int courseId = Integer.parseInt(req.getParameter("courseId"));
        String[] studentsId = req.getParameterValues("studentId");
        String[] grades = req.getParameterValues("grade");
        boolean result = courseService.updateGrades(courseId, studentsId, grades);

        if (result)
            session.setAttribute("message", "elective.account.course.grade.update.success");
        else
            session.setAttribute("message", "elective.account.course.grade.update.fail");

        resp.sendRedirect(req.getContextPath() + "/elective/account/course?id=" + courseId);
    }
}
