package ua.com.epam.project.utils;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class GeneratePdfSeveralCoursesTest {

    @Test
    void getPdf() throws DocumentException, IOException {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setName("test");
        courseDto.setDateStart(new Date());
        courseDto.setDateEnd(new Date());
        courseDto.setDescription("test description");
        courseDto.setCreated(new Date());
        courseDto.setStatus("Active");
        courseDto.setTeacherLogin("testTeacher");

        List<CourseDto> courseDtoList = Collections.singletonList(courseDto);

        UserDto userDto = new UserDto();
        userDto.setId(2);
        userDto.setFirstName("testName");
        userDto.setLastName("testLastName");
        userDto.setRole("STUDENT");

        Assertions.assertNotNull(GeneratePdfSeveralCourses.getPdf(courseDtoList, userDto));

        userDto.setRole("TEACHER");
        Assertions.assertNotNull(GeneratePdfSeveralCourses.getPdf(courseDtoList, userDto));
    }
}