package ua.com.epam.project.controller;

import org.apache.log4j.Logger;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;
import ua.com.epam.project.utils.VerifyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Login servlet
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final Logger LOG = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String error = (String) session.getAttribute("message");
        req.setAttribute("message", error);

        if (error != null)
            session.removeAttribute("message");

        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /**
     * Procedure to authorize
     *
     * @param req  request
     * @param resp response
     * @throws IOException exception can be thrown
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Integer attempts = (Integer) session.getAttribute("attempts");
        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");

        if (attempts == null) {
            attempts = 0;
            session.setAttribute("attempts", attempts);
        }

        boolean over = checkAttempts(++attempts);
        if (login == null || password == null || login.length() < 4 || password.length() < 4) {
            if (!over) {
                session.setAttribute("attempts", attempts);
                session.setAttribute("message", "login.invalid.data");
                resp.sendRedirect(req.getContextPath() + "/login");
            } else
                forwardToResetPassword(session, resp, req);
        } else {
            User user = userService.getUserByLoginAndPassword(login, password);
            boolean valid = VerifyUtils.verify(gRecaptchaResponse);

            if (!valid) {
                if (!over) {
                    session.setAttribute("attempts", attempts);
                    session.setAttribute("message", "login.captcha");
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else
                    forwardToResetPassword(session, resp, req);
            } else if (user == null) {
                if (!over) {
                    session.setAttribute("message", "login.invalid.data");
                    session.setAttribute("attempts", attempts);
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else
                    forwardToResetPassword(session, resp, req);
            } else if (!user.getStatus().name().equals("ACTIVE")) {
                if (!over) {
                    session.setAttribute("attempts", attempts);
                    session.setAttribute("message", "login.banned");
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else
                    forwardToResetPassword(session, resp, req);
            } else {
                session = req.getSession(true);
                user.setPassword(null);
                session.setAttribute("user", user);
                session.removeAttribute("attempts");
                LOG.info(user.getLogin() + " has passed authorization");

                if (user.getRoleId() == 1)
                    resp.sendRedirect(req.getContextPath() + "/admin_page.jsp");
                else
                    resp.sendRedirect(req.getContextPath() + "/elective/courses");
            }
        }
    }

    private synchronized void forwardToResetPassword(HttpSession session, HttpServletResponse resp, HttpServletRequest req) throws IOException {
        session.removeAttribute("attempts");
        session.setAttribute("message", "forgot.password.failed.attempts");
        resp.sendRedirect(req.getContextPath() + "/forgot_password");
    }

    private synchronized boolean checkAttempts(Integer attempts) {
        return attempts == 3;
    }
}