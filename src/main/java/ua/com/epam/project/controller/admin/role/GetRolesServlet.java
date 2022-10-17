package ua.com.epam.project.controller.admin.role;

import ua.com.epam.project.entity.Role;
import ua.com.epam.project.service.RoleService;
import ua.com.epam.project.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display all roles
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/roles")
public class GetRolesServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        List<Role> roleList = roleService.getAll();
        req.setAttribute("roleList", roleList);
        getServletContext().getRequestDispatcher("/roles.jsp").forward(req, resp);
    }
}