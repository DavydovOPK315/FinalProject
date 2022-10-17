package ua.com.epam.project.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncodingFilterTest {

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private ServletRequest servletRequest;

    @Mock
    private ServletResponse servletResponse;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private EncodingFilter encodingFilter;

    @Test
    void init() {
        String encoding = "UTF-8";
        when(filterConfig.getInitParameter("encoding")).thenReturn(null);
        assertDoesNotThrow(() -> encodingFilter.init(filterConfig));

        when(filterConfig.getInitParameter("encoding")).thenReturn(encoding);
        assertDoesNotThrow(() -> encodingFilter.init(filterConfig));
    }

    @Test
    void doFilter() throws ServletException, IOException {
        doNothing().when(filterChain).doFilter(servletRequest, servletResponse);
        assertDoesNotThrow(() -> encodingFilter.doFilter(servletRequest, servletResponse, filterChain));
    }
}