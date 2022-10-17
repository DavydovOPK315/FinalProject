package ua.com.epam.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class SendEmailUtilTest {

    @Mock
    private MimeMessage message;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendEmail() {
        try (MockedStatic<Transport> transport = Mockito.mockStatic(Transport.class)) {
            transport.when(() -> Transport.send(message)).thenAnswer((Answer<Void>) invocation -> null);
            Assertions.assertDoesNotThrow(() -> SendEmailUtil.sendEmail("davydov654@gmail.com", "subject", "content"));
        }
    }
}