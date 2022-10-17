package ua.com.epam.project.controller;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to filter courses
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/courses/filter")
public class ElectiveAccountCoursesFilterServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int userId = ((User) session.getAttribute("user")).getId();
        UserDto userDto = userService.getUserById(userId);
        String value = req.getParameter("value");
        List<CourseDto> courseList = courseService.getAllByStatusUserId(value, userId);

        req.setAttribute("userDto", userDto);
        req.setAttribute("courseList", courseList);
        getServletContext().getRequestDispatcher("/elective_account.jsp").forward(req, resp);
    }
}