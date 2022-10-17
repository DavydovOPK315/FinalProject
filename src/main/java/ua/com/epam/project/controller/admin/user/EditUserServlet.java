package ua.com.epam.project.controller.admin.user;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.RoleService;
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
 * Servlet to edit user
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/users/edit")
public class EditUserServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();
    private final UserService userService = ServiceFactory.getUserService();
    private final CourseService courseService = ServiceFactory.getCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int userId = Integer.parseInt(req.getParameter("id"));
        UserDto userDto = userService.getUserById(userId);
        List<Role> roles = roleService.getAll();
        Status[] statuses = Status.values();
        List<CourseDto> courseList = courseService.getAllByUserId(userId);

        req.setAttribute("userDto", userDto);
        req.setAttribute("statuses", statuses);
        req.setAttribute("roles", roles);
        req.setAttribute("courseList", courseList);
        getServletContext().getRequestDispatcher("/user_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int userId = Integer.parseInt(req.getParameter("id"));
        String login = req.getParameter("login").trim();
        String firstName = req.getParameter("first_name").trim();
        String lastName = req.getParameter("last_name").trim();
        String email = req.getParameter("email").trim();
        String status = req.getParameter("status").trim();
        int roleId = Integer.parseInt(req.getParameter("role"));

        if (login.length() < 4 || firstName.length() < 4 || lastName.length() < 4 || status.length() < 4)
            session.setAttribute("message", "registration.invalid.data");
        else {
            User user = new User();
            user.setId(userId);
            user.setLogin(login.trim());
            user.setFirstName(firstName.trim());
            user.setLastName(lastName.trim());
            user.setEmail(email.trim());
            user.setStatus(Status.valueOf(status));
            user.setRoleId(roleId);

            boolean result = userService.updateUser(user);
            if (result)
                session.setAttribute("message", "admin.user.edit.updated.success");
            else
                session.setAttribute("message", "admin.user.edit.updated.unable");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users/edit?id=" + userId);
    }
}