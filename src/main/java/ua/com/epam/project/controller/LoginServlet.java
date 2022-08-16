package ua.com.epam.project.controller;

import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userService.getUserByLoginAndPassword(login, password);

        if (user == null) {
            req.setAttribute("message", "Your username and password do not match. Try again.");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);

        } else {
            // add to session
            resp.sendRedirect(req.getContextPath() + "/main.jsp");
        }
    }
}