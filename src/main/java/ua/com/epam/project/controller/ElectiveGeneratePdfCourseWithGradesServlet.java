package ua.com.epam.project.controller;

import com.itextpdf.text.DocumentException;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.utils.GeneratePdfCourseWithGrades;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display the PDF report with course info
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/account/course/print/course/grade/report")
public class ElectiveGeneratePdfCourseWithGradesServlet extends HttpServlet {
    private final CourseService courseService = ServiceFactory.getCourseService();
    private final TopicService topicService = ServiceFactory.getTopicService();
    private final Logger LOG = Logger.getLogger(ElectiveGeneratePdfCourseWithGradesServlet.class);

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            int courseId = Integer.parseInt(req.getParameter("courseId"));
            List<UserDto> students = courseService.getAllStudentsWithGradesByCourseId(courseId);
            UserDto userResult = students.stream().filter(userDto -> userDto.getId() == user.getId()).findFirst().orElse(null);
            List<Topic> topics = topicService.getAllByCourseId(courseId);
            CourseDto courseDto = courseService.getCourseById(courseId);

            response.setContentType("application/pdf;charset=UTF-8");
            response.addHeader("Content-Disposition", "inline; filename=" + "report.pdf");
            ServletOutputStream out = response.getOutputStream();
            ByteArrayOutputStream baos = GeneratePdfCourseWithGrades.getPdf(courseDto, students, userResult, topics);
            LOG.info("GeneratePdfCourseWithGrades: Successfully generated a PDF file for course with grades");
            baos.writeTo(out);
        } catch (DocumentException e) {
            LOG.error("GeneratePdfCourseWithGrades: Error with generating PDF file for course with grades");
            throw new DocumentException("Error with generating pdf for course with grades");
        }
    }
}