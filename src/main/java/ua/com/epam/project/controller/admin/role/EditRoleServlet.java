package ua.com.epam.project.controller.admin.role;

import ua.com.epam.project.entity.Role;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.service.RoleService;
import ua.com.epam.project.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to edit role
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/roles/edit")
public class EditRoleServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int roleId = Integer.parseInt(req.getParameter("id"));
        Role role = roleService.getRoleById(roleId);
        Status[] statuses = Status.values();

        req.setAttribute("role", role);
        req.setAttribute("statuses", statuses);
        getServletContext().getRequestDispatcher("/role_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int roleId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String status = req.getParameter("status");

        if (name.length() < 3 || status.length() == 0)
            session.setAttribute("message", "registration.invalid.data");
        else {
            boolean result = roleService.updateRole(roleId, name.trim(), status);

            if (result)
                session.setAttribute("message", "admin.roles.edit.success");
            else
                session.setAttribute("message", "admin.roles.edit.unable");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/roles/edit?id=" + roleId);
    }
}