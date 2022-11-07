package ua.com.epam.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class RegistrationServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ServletContext servletContext;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Spy
    @InjectMocks
    private RegistrationServlet registrationServlet;

    @Test
    void doGet() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("message")).thenReturn("myMessage");
        doReturn(servletContext).when(registrationServlet).getServletContext();
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        assertDoesNotThrow(() -> registrationServlet.doGet(req, resp));
        verify(registrationServlet, times(1)).getServletContext();
        verify(servletContext, times(1)).getRequestDispatcher(anyString());
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @Test
    void doPostInvalidData() throws IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(anyString())).thenReturn("row");

        assertDoesNotThrow(() -> registrationServlet.doPost(req, resp));
        verify(req, times(1)).getSession();
        verify(req, times(5)).getParameter(anyString());
        verify(req, times(1)).getContextPath();
        verify(resp, times(1)).sendRedirect(anyString());
    }

    @Test
    void doPostValidDataSuccess() throws Exception {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(anyString())).thenReturn("value");
        doReturn(true).when(userService).registerUser(any(User.class));

        setFinalStatic(RegistrationServlet.class.getDeclaredField("userService"), userService);

        assertDoesNotThrow(() -> registrationServlet.doPost(req, resp));
        verify(req, times(1)).getSession();
        verify(req, times(5)).getParameter(anyString());
        verify(req, times(1)).getContextPath();
        verify(resp, times(1)).sendRedirect(anyString());
    }

    @Test
    void doPostValidDataFail() throws Exception {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter(anyString())).thenReturn("value");
        doReturn(false).when(userService).registerUser(any(User.class));

        setFinalStatic(RegistrationServlet.class.getDeclaredField("userService"), userService);

        assertDoesNotThrow(() -> registrationServlet.doPost(req, resp));
        verify(req, times(1)).getSession();
        verify(req, times(5)).getParameter(anyString());
        verify(req, times(1)).getContextPath();
        verify(resp, times(1)).sendRedirect(anyString());
    }
}