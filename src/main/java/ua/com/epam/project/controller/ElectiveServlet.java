package ua.com.epam.project.controller;

import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.service.CourseService;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet to prepare main page
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/elective/courses")
public class ElectiveServlet extends HttpServlet {
    private static final CourseService courseService = ServiceFactory.getCourseService();
    private static final UserService userService = ServiceFactory.getUserService();
    private static final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setIntHeader("Refresh", 3610);
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        List<CourseDto> courseList = courseService.getAll().stream()
                .sorted(Comparator.comparing(CourseDto::getStatus).reversed())
                .collect(Collectors.toList());
        List<UserDto> teachers = userService.getAllByRole("TEACHER");
        List<Topic> topics = topicService.getAll();

        int page = 1;

        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        int recordsPerPage = 6;
        int noOfRecords = courseList.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        int fromIndex = (page - 1) * recordsPerPage;
        int endIndex = Math.min(fromIndex + recordsPerPage, noOfRecords);
        List<CourseDto> resultList = courseList.subList(fromIndex, endIndex);
        String servletPath = req.getRequestURI().substring(req.getContextPath().length());

        req.setAttribute("servletPath", servletPath);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("listSize", courseList.size());
        req.setAttribute("courseList", resultList);
        req.setAttribute("teachers", teachers);
        req.setAttribute("topics", topics);
        req.setAttribute("dateNow", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        getServletContext().getRequestDispatcher("/elective_courses.jsp").forward(req, resp);
    }
}