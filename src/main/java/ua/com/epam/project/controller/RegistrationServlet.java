package ua.com.epam.project.controller;

import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String login = req.getParameter("login");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email= req.getParameter("email");
        String password= req.getParameter("password");

        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        userService.registerUser(user);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
