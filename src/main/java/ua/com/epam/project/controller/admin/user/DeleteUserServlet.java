package ua.com.epam.project.controller.admin.user;

import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to delete user
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/users/delete")
public class DeleteUserServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int userId = Integer.parseInt(req.getParameter("id"));

        boolean result = userService.deleteUser(userId);
        if (result) {
            session.setAttribute("message", "admin.user.edit.deleted.success");
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
            session.setAttribute("message", "admin.user.edit.deleted.unable");
            resp.sendRedirect(req.getContextPath() + "/admin/users/edit?id=" + userId);
        }
    }
}