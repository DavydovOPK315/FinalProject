package ua.com.epam.project.controller.admin.role;

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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet to create new role
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/roles/new")
public class NewRoleServlet extends HttpServlet {
    private final RoleService roleService = ServiceFactory.getRoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Status[] statuses = Status.values();
        req.setAttribute("statuses", statuses);
        req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        getServletContext().getRequestDispatcher("/role_new.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String name = req.getParameter("name").trim();
        String status = req.getParameter("status").trim();

        if (name.length() < 3 || status.length() == 0)
            session.setAttribute("message", "registration.invalid.data");
        else {
            boolean result = roleService.createRole(name.trim(), status);

            if (result)
                session.setAttribute("message", "admin.roles.add.success");
            else
                session.setAttribute("message", "admin.roles.add.unable");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/roles");
    }
}
