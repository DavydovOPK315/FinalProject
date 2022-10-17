package ua.com.epam.project.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dto.CourseDto;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class DatesTagTest {

    @Mock
    private PageContext pageContext;

    @Mock
    private JspWriter jspWriter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doStartTag() throws JspException, IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(2);
        courseDto.setDateStart(new Date());
        courseDto.setDateEnd(new Date());

        DatesTag datesTag = new DatesTag();
        datesTag.setCourseDto(courseDto);

        (Mockito.doReturn(jspWriter).when(pageContext)).getOut();
        datesTag.setPageContext(pageContext);
        assertEquals(0, datesTag.doStartTag());

        Mockito.doThrow(new IOException()).when(jspWriter).print(anyString());
        assertDoesNotThrow(datesTag::doStartTag);
    }
}