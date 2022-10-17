package ua.com.epam.project.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContextListenerTest {

    @Mock
    private ServletContextEvent sce;

    @Mock
    private ServletContext context;

    @InjectMocks
    private ContextListener contextListener;

    @Test
    void contextInitialized() {
        String path = "path";

        when(sce.getServletContext())
                .thenReturn(context);
        when(context.getAttribute("app"))
                .thenReturn(path);

        assertDoesNotThrow(() -> contextListener.contextInitialized(sce));
        assertEquals(path, context.getAttribute("app"));
    }
}