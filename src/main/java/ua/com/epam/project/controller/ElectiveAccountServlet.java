package ua.com.epam.project.controller;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display account page
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account")
public class ElectiveAccountServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setIntHeader("Refresh", 1800);
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        String courseName = (String) session.getAttribute("courseName");
        req.setAttribute("message", message);
        req.setAttribute("courseName", courseName);

        if (message != null) {
            session.removeAttribute("message");
            session.removeAttribute("courseName");
        }

        int userId = ((User) session.getAttribute("user")).getId();
        UserDto userDto = userService.getUserById(userId);
        List<CourseDto> courseList = courseService.getAllByUserId(userId);

        req.setAttribute("userDto", userDto);
        req.setAttribute("courseList", courseList);
        getServletContext().getRequestDispatcher("/elective_account.jsp").forward(req, resp);
    }
}