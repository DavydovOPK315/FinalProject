package ua.com.epam.project.controller.admin.user;

import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet to filter users
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/users/filter")
public class FilterUsersServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> userDtoList = new ArrayList<>();
        String key = req.getParameter("key");
        String value = req.getParameter("value");

        switch (key) {
            case "status":
                userDtoList = userService.getAllByStatus(value);
                break;
            case "role":
                userDtoList = userService.getAllByRole(value);
                break;
            case "login":
                UserDto userDto = userService.getUserByLogin(value);
                if (userDto != null)
                    userDtoList.add(userDto);
                break;
        }

        if (userDtoList.isEmpty())
            req.setAttribute("message", "admin.users.no.found");

        req.setAttribute("userDtoList", userDtoList);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}