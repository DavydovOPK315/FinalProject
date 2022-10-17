package ua.com.epam.project.controller.admin.user;

import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.entity.User;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet to create new user
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/users/new")
public class NewUserServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        List<Role> roles = roleService.getAll();
        Status[] statuses = Status.values();
        req.setAttribute("statuses", statuses);
        req.setAttribute("roles", roles);
        req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        getServletContext().getRequestDispatcher("/user_new.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login").trim();
        String firstName = req.getParameter("first_name").trim();
        String lastName = req.getParameter("last_name").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();
        String status = req.getParameter("status").trim();
        int roleId = Integer.parseInt(req.getParameter("role"));

        if (login.length() < 4 || firstName.length() < 4 || lastName.length() < 4 || password.length() < 4 || status.length() < 4)
            session.setAttribute("message", "registration.invalid.data");
        else {
            User user = new User();
            user.setLogin(login.trim());
            user.setFirstName(firstName.trim());
            user.setLastName(lastName.trim());
            user.setEmail(email.trim());
            user.setPassword(password.trim());
            user.setStatus(Status.valueOf(status));
            user.setRoleId(roleId);

            boolean result = userService.registerUser(user);
            if (result) {
                session.setAttribute("message", "admin.users.add.user.success");
                int id = userService.getUserByLogin(login.trim()).getId();
                resp.sendRedirect(req.getContextPath() + "/admin/users/edit?id=" + id);
            } else {
                session.setAttribute("message", "admin.users.add.user.unable");
                resp.sendRedirect(req.getContextPath() + "/admin/users/new");
            }
        }
    }
}