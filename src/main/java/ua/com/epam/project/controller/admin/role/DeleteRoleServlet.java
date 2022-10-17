package ua.com.epam.project.controller.admin.role;

import ua.com.epam.project.service.RoleService;
import ua.com.epam.project.service.ServiceFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to delete role
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/roles/delete")
public class DeleteRoleServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int roleId = Integer.parseInt(req.getParameter("id"));

        boolean result = roleService.deleteRole(roleId);

        if (result) {
            session.setAttribute("message", "admin.roles.delete.success");
            resp.sendRedirect(req.getContextPath() + "/admin/roles");
        } else {
            session.setAttribute("message", "admin.roles.delete.unable");
            resp.sendRedirect(req.getContextPath() + "/admin/roles/edit?id=" + roleId);
        }
    }
}
