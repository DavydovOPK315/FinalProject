package ua.com.epam.project.filter;

import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.ServiceFactory;
import ua.com.epam.project.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Authentication Filter.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class AuthenticationFilter implements Filter {
    private static final UserService userService = ServiceFactory.getUserService();
    private static final List<String> guestEndpoints;
    private static final List<String> adminEndpoints;

    static {
        guestEndpoints = new ArrayList<>();
        guestEndpoints.add("/login");
        guestEndpoints.add("/login.jsp");
        guestEndpoints.add("/registration");
        guestEndpoints.add("/registration.jsp");
        guestEndpoints.add("/reset_password");
        guestEndpoints.add("/forgot_password");
        guestEndpoints.add("/forgot_password.jsp");
        guestEndpoints.add("/forgot_password_form.jsp");
        guestEndpoints.add("/checkPasswordMatch.js");
        guestEndpoints.add("/locale");

        adminEndpoints = new ArrayList<>();
        adminEndpoints.add("/course_edit.jsp");
        adminEndpoints.add("/course_new.jsp");
        adminEndpoints.add("/courses.jsp");
        adminEndpoints.add("/role_edit.jsp");
        adminEndpoints.add("/role_new.jsp");
        adminEndpoints.add("/roles.jsp");
        adminEndpoints.add("/topic_edit.jsp");
        adminEndpoints.add("/topic_new.jsp");
        adminEndpoints.add("/topics.jsp");
        adminEndpoints.add("/user_edit.jsp");
        adminEndpoints.add("/user_new.jsp");
        adminEndpoints.add("/users.jsp");
        adminEndpoints.add("/admin_page.jsp");
    }

    /**
     * Procedure for Access Filtering
     *
     * @param servletRequest  servlet request
     * @param servletResponse servlet response
     * @param filterChain     filter chain
     * @throws IOException      exception can be thrown
     * @throws ServletException exception can be thrown
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String path = request.getServletPath();
        boolean isStaticResource = path.startsWith("/static/");

        if (isStaticResource) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (user == null) {
            boolean result = guestEndpoints.stream().anyMatch(s -> s.equals(path));
            if (result)
                filterChain.doFilter(servletRequest, servletResponse);
            else {
                request.setAttribute("message", "filter.no.login");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else if (user.getRoleId() != 1) {

            boolean result = adminEndpoints.stream().anyMatch(s -> s.equals(path));
            boolean filter = path.contains("/admin/courses/filter");
            boolean locale = path.equals("/locale");
            boolean login = path.equals("/login");
            UserDto actualUser = userService.getUserById(user.getId());

            if (filter || locale || login)
                filterChain.doFilter(request, response);
            else if (path.contains("/admin") || result) {
                request.setAttribute("message", "filter.admin");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (actualUser.getStatus().equals("BANNED")) {
                request.setAttribute("message", "login.banned");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if (!user.getLogin().equals(actualUser.getLogin()) || !user.getFirstName().equals(actualUser.getFirstName()) || !user.getLastName().equals(actualUser.getLastName()) || !user.getEmail().equals(actualUser.getEmail())) {
                user = userService.getUserByEmail(actualUser.getEmail());
                user.setPassword(null);
                session.setAttribute("user", user);
                filterChain.doFilter(request, response);
            } else
                filterChain.doFilter(request, response);
        } else
            filterChain.doFilter(request, response);
    }
}