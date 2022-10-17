package ua.com.epam.project.tag;

import org.apache.log4j.Logger;
import ua.com.epam.project.dto.CourseDto;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.*;

/**
 * Dates tag.
 *
 * @author Denis Davydov
 * @version 2.0
 */
public class DatesTag extends TagSupport {
    private static final Logger LOG = Logger.getLogger(DatesTag.class);
    private CourseDto courseDto;

    public void setCourseDto(CourseDto courseDto) {
        this.courseDto = courseDto;
    }

    /**
     * Function to print course date started and date ended
     *
     * @return return calling method doStartTag() of super class
     * @throws JspException exception can be thrown
     */
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print(courseDto.getDateStart() + " " + courseDto.getDateEnd());
        } catch (IOException e) {
            LOG.warn("DatesTag problem");
        }
        return super.doStartTag();
    }
}