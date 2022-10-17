package ua.com.epam.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.Json;
import javax.json.JsonReader;
import java.io.InputStream;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyUtilsTest {

    @Mock
    private InputStream is;

    @Mock
    private JsonReader jsonReader;

    @Test
    void verify() {
        mockStatic(Json.class);
        when(Json.createReader(is)).thenReturn(jsonReader);

        Assertions.assertFalse(VerifyUtils.verify("recaptcha"));
        Assertions.assertFalse(VerifyUtils.verify(null));
    }
}