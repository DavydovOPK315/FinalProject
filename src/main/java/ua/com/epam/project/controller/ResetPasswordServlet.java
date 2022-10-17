package ua.com.epam.project.controller;

import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Reset password servlet
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/reset_password")
public class ResetPasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        User user = userService.getUserByToken(token);
        req.setAttribute("token", token);

        if (user == null) {
            req.setAttribute("message", "login.invalid.token");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("validLink", true);
            getServletContext().getRequestDispatcher("/reset_password_form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String token = req.getParameter("token");

        if (token == null || token.isEmpty()) {
            req.setAttribute("validLink", false);
            req.setAttribute("message", "login.invalid.token");
            getServletContext().getRequestDispatcher("/reset_password_form.jsp").forward(req, resp);
        } else {
            String password = req.getParameter("password");
            User result = userService.getUserByToken(token);

            if (result == null)
                session.setAttribute("message", "login.invalid.token");
            else {
                userService.updatePassword(result, password);
                session.setAttribute("message", "login.changed.password");
            }
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}