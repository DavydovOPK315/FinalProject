package ua.com.epam.project.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Context listener
 *
 * @author Denis Davydov
 * @version 2.0
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Procedure to add context path to context attribute
     *
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getContextPath();
        context.setAttribute("app", path);
    }
}