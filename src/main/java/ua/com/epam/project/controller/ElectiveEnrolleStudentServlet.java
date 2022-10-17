package ua.com.epam.project.controller;

import org.apache.log4j.Logger;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.utils.SendEmailUtil;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for enrolling in a course
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/enrolle")
public class ElectiveEnrolleStudentServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();
    private static final Logger LOG = Logger.getLogger(ElectiveEnrolleStudentServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String to = user.getEmail();
        int courseId = Integer.parseInt(req.getParameter("id"));
        boolean result = courseService.addStudent(courseId, user.getId());
        CourseDto course = courseService.getCourseById(courseId);
        String courseName = course.getName();
        session.setAttribute("courseName", courseName);

        if (result) {
            session.setAttribute("message", "elective.account.enroll.success");
            String subject = "Course information";
            String content = "<p>Hello, " + user.getFirstName() + " " + user.getLastName() + "!</p>"
                    + "<p>You have successfully enrolled in the course \"" + course.getName() + "\".</p>"
                    + "<p>Good luck have fun!</p>";
            try {
                SendEmailUtil.sendEmail(to, subject, content);
            } catch (MessagingException e) {
                LOG.error("The enrollment message for the course cannot be sent.");
            }
        } else
            session.setAttribute("message", "elective.account.enroll.registered.unable");

        resp.sendRedirect(req.getContextPath() + "/elective/account");
    }
}