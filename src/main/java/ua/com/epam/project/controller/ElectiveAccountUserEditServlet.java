package ua.com.epam.project.controller;

import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for editing an account
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/user/edit")
public class ElectiveAccountUserEditServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        getServletContext().getRequestDispatcher("/elective_account_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        User user = new User();
        user.setId(sessionUser.getId());
        String login = req.getParameter("login").trim();
        String firstName = req.getParameter("first_name").trim();
        String lastName = req.getParameter("last_name").trim();
        String email = req.getParameter("email").trim();
        String password = req.getParameter("password").trim();

        if (login.length() < 4 || firstName.length() < 4 || lastName.length() < 4 || password.length() < 4)
            session.setAttribute("message", "registration.invalid.data");
        else {
            user.setLogin(login.trim());
            user.setFirstName(firstName.trim());
            user.setLastName(lastName.trim());
            user.setEmail(email.trim());
            user.setPassword(password);

            boolean result = userService.updateUser(user);
            if (result) {
                sessionUser = userService.getUserByEmail(email);
                sessionUser.setPassword(null);
                session.setAttribute("user", sessionUser);
                session.setAttribute("message", "elective.account.updated");
            } else
                session.setAttribute("message", "registration.invalid.login.email");
        }
        resp.sendRedirect(req.getContextPath() + "/elective/account/user/edit");
    }
}