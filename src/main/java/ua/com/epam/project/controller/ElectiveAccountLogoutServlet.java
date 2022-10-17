package ua.com.epam.project.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to logout
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/logout")
public class ElectiveAccountLogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.setAttribute("message", "login.logout");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
