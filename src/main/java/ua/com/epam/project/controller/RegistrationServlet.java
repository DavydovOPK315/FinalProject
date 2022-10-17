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
 * Registration servlet
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        getServletContext().getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login").trim();
        String firstName = req.getParameter("first_name").trim();
        String lastName = req.getParameter("last_name").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (login.length() < 4 || firstName.length() < 4 || lastName.length() < 4 || password.length() < 4) {
            session.setAttribute("message", "registration.invalid.data");
            resp.sendRedirect(req.getContextPath() + "/registration");
        } else {
            User user = new User();
            user.setLogin(login);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);

            boolean result = userService.registerUser(user);
            if (result) {
                session.setAttribute("message", "registration.success");
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                session.setAttribute("message", "registration.invalid.login.email");
                resp.sendRedirect(req.getContextPath() + "/registration");
            }
        }
    }
}