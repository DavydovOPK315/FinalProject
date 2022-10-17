package ua.com.epam.project.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;
import ua.com.epam.project.utils.GetSiteUrlUtil;
import ua.com.epam.project.utils.SendEmailUtil;

import javax.mail.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to send email with reset link for password recovery
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/forgot_password")
public class ForgotPasswordServlet extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final static Logger LOG = Logger.getLogger(ForgotPasswordServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        String error = (String) session.getAttribute("error");
        req.setAttribute("error", error);

        if (error != null)
            session.removeAttribute("error");

        getServletContext().getRequestDispatcher("/forgot_password_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            String to = req.getParameter("email");
            User user = userService.getUserByEmail(to);

            if (user == null) throw new IllegalArgumentException("");

            String token = RandomStringUtils.random(30, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
            userService.updateResetPasswordToken(token, user.getId());
            String resetPasswordLink = GetSiteUrlUtil.getSiteURL(req) + "/reset_password?token=" + token;
            String subject = "Here's the link to reset your password";
            String content = "<p>Hello, " + user.getFirstName() + " " + user.getLastName() + "!</p>" + "<p>You have requested to reset your password.</p>" + "<p>Click the link below to change your password:</p>" + "<p><a href=\""
                    + resetPasswordLink
                    + "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, " + "or you have not made the request.</p>";

            SendEmailUtil.sendEmail(to, subject, content);
            session.setAttribute("message", "forgot.password.reset.link");
            LOG.info("Password reset link sent");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", "forgot.password.send.error.mail");
            LOG.warn("Wrong email for sending email to reset password");
        } catch (MessagingException e) {
            session.setAttribute("error", "forgot.password.send.error.system");
            LOG.error("Problem sending email");
        }
        resp.sendRedirect(req.getContextPath() + "/forgot_password");
    }
}