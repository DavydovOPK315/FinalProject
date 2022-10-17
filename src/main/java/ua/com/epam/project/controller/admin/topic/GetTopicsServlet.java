package ua.com.epam.project.controller.admin.topic;

import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display all topics
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/topics")
public class GetTopicsServlet extends HttpServlet {
    private final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        List<Topic> topicList = topicService.getAll();
        req.setAttribute("topicList", topicList);
        getServletContext().getRequestDispatcher("/topics.jsp").forward(req, resp);
    }
}
