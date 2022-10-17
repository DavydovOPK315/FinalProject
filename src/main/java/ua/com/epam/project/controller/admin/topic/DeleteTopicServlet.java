package ua.com.epam.project.controller.admin.topic;

import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to delete topic
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/topics/delete")
public class DeleteTopicServlet extends HttpServlet {
    private final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int topicId = Integer.parseInt(req.getParameter("id"));

        boolean result = topicService.deleteTopic(topicId);

        if (result) {
            session.setAttribute("message", "admin.topics.delete.success");
            resp.sendRedirect(req.getContextPath() + "/admin/topics");
        } else {
            session.setAttribute("message", "admin.topics.delete.unable");
            resp.sendRedirect(req.getContextPath() + "/admin/topics/edit?id=" + topicId);
        }
    }
}
