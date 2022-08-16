package ua.com.epam.project.controller;

import ua.com.epam.project.service.UserService;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String to = req.getParameter("email");
        String from = "gameshopcontactmail@gmail.com";

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true"); // enable TLS

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "rqapzljcxguzyasz");
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "Final Project"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Here's the link to reset your password");
            String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>" + "<p>Click the link below to change your password:</p>" + "<p><a href=\""
                    + "http://localhost:8080/FinalProject/forgot_password_form.jsp"
                    + "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, " + "or you have not made the request.</p>";

            message.setContent(content, "text/html;charset=utf-8");
            message.saveChanges();
            Transport.send(message);
            req.setAttribute("message", "Have sent a reset password link to your email. Please check");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            req.setAttribute("error", "Error while sending email");
        }
        getServletContext().getRequestDispatcher("/forgot_password_form.jsp").forward(req, resp);
    }
}