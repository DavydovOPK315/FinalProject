package ua.com.epam.project.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Locale servlet
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("lang", req.getParameter("lang"));
        resp.sendRedirect(req.getHeader("referer"));
    }
}