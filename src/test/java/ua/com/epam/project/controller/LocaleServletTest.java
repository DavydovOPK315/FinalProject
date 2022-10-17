package ua.com.epam.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocaleServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LocaleServlet localeServlet;

    @Test
    void doPost() throws IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getHeader("referer")).thenReturn("header");

        assertDoesNotThrow(() -> localeServlet.doPost(req, resp));
        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute(anyString(), any());
        verify(req, times(1)).getParameter("lang");
        verify(req, times(1)).getHeader("referer");
        verify(resp, times(1)).sendRedirect(anyString());
    }
}