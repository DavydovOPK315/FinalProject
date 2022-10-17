package ua.com.epam.project.controller;

import com.itextpdf.text.DocumentException;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;
import ua.com.epam.project.utils.GeneratePdfSeveralCourses;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

/**
 * Servlet to display the PDF report with courses
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/print/courses/report")
public class ElectiveGeneratePdfSeveralCoursesServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getUserService();
    private final CourseService courseService = ServiceFactory.getCourseService();
    private final Logger LOG = Logger.getLogger(ElectiveGeneratePdfSeveralCoursesServlet.class);

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            int userId = ((User) session.getAttribute("user")).getId();
            UserDto userDto = userService.getUserById(userId);
            List<CourseDto> courseList = courseService.getAllByUserId(userId);

            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "inline; filename=" + "report.pdf");
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream baos = GeneratePdfSeveralCourses.getPdf(courseList, userDto);
            LOG.info("GeneratePdfSeveralCourses: Successfully generated a PDF file with several courses");
            baos.writeTo(out);
        } catch (DocumentException e) {
            LOG.error("GeneratePdfSeveralCourses: Error with generating PDF file with several courses");
            throw new DocumentException("Error with generating pdf with several courses");
        }
    }
}