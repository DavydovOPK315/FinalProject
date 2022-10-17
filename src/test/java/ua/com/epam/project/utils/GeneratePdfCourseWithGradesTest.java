package ua.com.epam.project.utils;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.Performance;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.Topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class GeneratePdfCourseWithGradesTest {

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

        UserDto userDto = new UserDto();
        userDto.setId(2);
        userDto.setFirstName("testName");
        userDto.setLastName("testLastName");

        Performance performance = new Performance();
        performance.setPerformanceId(3);
        performance.setGrade(60);
        performance.setTopicId(2);
        performance.setTopicName("testTopic");

        userDto.setPerformanceList(Collections.singletonList(performance));

        List<UserDto> students = new ArrayList<>();
        students.add(userDto);

        Topic topic = new Topic();
        topic.setName("testName");
        List<Topic> topics = Collections.singletonList(topic);

        Assertions.assertNotNull(GeneratePdfCourseWithGrades.getPdf(courseDto, students, userDto, topics));
        Assertions.assertNotNull(GeneratePdfCourseWithGrades.getPdf(courseDto, students, null, topics));
    }
}