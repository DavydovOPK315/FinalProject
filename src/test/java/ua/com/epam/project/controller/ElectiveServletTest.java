package ua.com.epam.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.service.TopicService;
import ua.com.epam.project.service.impl.CourseServiceImpl;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class ElectiveServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private CourseServiceImpl courseService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TopicService topicService;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Spy
    @InjectMocks
    private ElectiveServlet electiveServlet;

    @Test
    void doGet() throws Exception {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("page")).thenReturn("1");
        when(req.getRequestURI()).thenReturn("requestURI");
        when(req.getContextPath()).thenReturn("path");
        when(session.getAttribute("message")).thenReturn("result");
        doReturn(servletContext).when(electiveServlet).getServletContext();
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        setFinalStatic(electiveServlet.getClass().getDeclaredField("courseService"), courseService);
        setFinalStatic(electiveServlet.getClass().getDeclaredField("userService"), userService);
        setFinalStatic(electiveServlet.getClass().getDeclaredField("topicService"), topicService);

        assertDoesNotThrow(() -> electiveServlet.doGet(req, resp));

        verify(electiveServlet, times(1)).getServletContext();
        verify(servletContext, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(req, resp);
    }
}