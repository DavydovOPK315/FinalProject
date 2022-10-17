package ua.com.epam.project.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding Filter.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class EncodingFilter implements Filter {
    private static final String ENCODING_UTF_8 = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE = "text/html; charset=UTF-8";
    private String defaultEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultEncoding = filterConfig.getInitParameter("encoding");

        if (defaultEncoding == null)
            defaultEncoding = ENCODING_UTF_8;
    }

    /**
     * Procedure to set default encoding
     *
     * @param servletRequest  servlet request
     * @param servletResponse servlet response
     * @param filterChain     filter chain
     * @throws IOException      exception can be thrown
     * @throws ServletException exception can be thrown
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(defaultEncoding);
        servletResponse.setContentType(DEFAULT_CONTENT_TYPE);
        servletResponse.setCharacterEncoding(defaultEncoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}