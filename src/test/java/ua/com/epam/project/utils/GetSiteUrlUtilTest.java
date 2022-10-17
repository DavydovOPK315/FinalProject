package ua.com.epam.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

class GetSiteUrlUtilTest {

    @Test
    void getSiteURL() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter/4.8.0"));
        Mockito.when(request.getServletPath()).thenReturn("artifact/org.mockito/mockito-junit-jupiter/4.8.0");
        Assertions.assertEquals("https://mvnrepository.com/", GetSiteUrlUtil.getSiteURL(request));
    }
}