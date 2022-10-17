package ua.com.epam.project.controller.admin.topic;

import ua.com.epam.project.entity.Topic;
import ua.com.epam.project.entity.Status;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to edit topic
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/admin/topics/edit")
public class EditTopicServlet extends HttpServlet {
    private final TopicService topicService = ServiceFactory.getTopicService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String message = (String) session.getAttribute("message");
        req.setAttribute("message", message);

        if (message != null)
            session.removeAttribute("message");

        int topicId = Integer.parseInt(req.getParameter("id"));
        Topic topic = topicService.getTopicById(topicId);
        Status[] statuses = Status.values();

        req.setAttribute("topic", topic);
        req.setAttribute("statuses", statuses);
        getServletContext().getRequestDispatcher("/topic_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int topicId = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        String status = req.getParameter("status").trim();

        if (name.length() < 3 || status.length() == 0)
            session.setAttribute("message", "registration.invalid.data");
        else {
            boolean result = topicService.updateTopic(topicId, name.trim(), status);

            if (result)
                session.setAttribute("message", "admin.topics.edit.success");
            else
                session.setAttribute("message", "admin.topics.edit.unable");
        }
        resp.sendRedirect(req.getContextPath() + "/admin/topics/edit?id=" + topicId);
    }
}