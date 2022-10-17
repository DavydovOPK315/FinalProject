package ua.com.epam.project.controller.admin.user;

import ua.com.epam.project.dto.UserDto;
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
 * Servlet to display all users
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/users")
public class GetUsersServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        List<UserDto> userDtoList = userService.getAll();
        req.setAttribute("userDtoList", userDtoList);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}