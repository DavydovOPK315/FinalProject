package ua.com.epam.project.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dao.CourseDao;
import ua.com.epam.project.dto.CourseDto;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.service.CourseService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private CourseServiceImpl service;

    private final CourseDto courseDto;
    private final List<CourseDto> courseDtoList;

    {
        courseDto = new CourseDto();
        courseDto.setId(10);
        courseDto.setName("Kotlin");
        courseDto.setStatus("ACTIVE");

        courseDtoList = new ArrayList<>();
        courseDtoList.add(courseDto);
    }

    @BeforeEach
    void setUp() throws Exception {
        setFinalStatic(CourseServiceImpl.class.getDeclaredField("courseDao"), courseDao);
    }

    @Test
    void getInstance() {
        CourseService courseService = CourseServiceImpl.getInstance();
        assertNotNull(courseService);
    }

    @Test
    void createCourse() {
        CourseDto emptyCourseDto = new CourseDto();

        when(courseDao.createCourse(courseDto)).thenReturn(true);
        when(courseDao.createCourse(emptyCourseDto)).thenReturn(false);

        assertTrue(service.createCourse(courseDto));
        assertFalse(service.createCourse(emptyCourseDto));
    }

    @Test
    void updateCourse() {
        CourseDto emptyCourseDto = new CourseDto();

        when(courseDao.updateCourse(courseDto)).thenReturn(true);
        when(courseDao.updateCourse(emptyCourseDto)).thenReturn(false);

        assertTrue(service.updateCourse(courseDto));
        assertFalse(service.updateCourse(emptyCourseDto));
    }

    @Test
    void updateGrades() {
        String[] studentsId = new String[]{};
        String[] grades = new String[]{};

        when(courseDao.updateGrades(10, studentsId, grades)).thenReturn(true);
        when(courseDao.updateGrades(-10, null, null)).thenReturn(false);

        assertTrue(service.updateGrades(10, studentsId, grades));
        assertFalse(service.updateGrades(-10, null, null));
    }

    @Test
    void deleteCourse() {
        when(courseDao.deleteCourse(10)).thenReturn(true);
        when(courseDao.deleteCourse(-10)).thenReturn(false);

        assertTrue(service.deleteCourse(10));
        assertFalse(service.deleteCourse(-10));
    }

    @Test
    void deleteTopicFromCourse() {
        when(courseDao.deleteTopicFromCourse(10, 5)).thenReturn(true);
        when(courseDao.deleteTopicFromCourse(-10, -5)).thenReturn(false);

        assertTrue(service.deleteTopicFromCourse(10, 5));
        assertFalse(service.deleteTopicFromCourse(-10, -5));
    }

    @Test
    void addStudent() {
        when(courseDao.addStudent(10, 5)).thenReturn(true);
        when(courseDao.addStudent(-10, -5)).thenReturn(false);

        assertTrue(service.addStudent(10, 5));
        assertFalse(service.addStudent(-10, -5));
    }

    @Test
    void leaveCourse() {
        when(courseDao.leaveCourse(10, 5)).thenReturn(true);
        when(courseDao.leaveCourse(-10, -5)).thenReturn(false);

        assertTrue(service.leaveCourse(10, 5));
        assertFalse(service.leaveCourse(-10, -5));
    }

    @Test
    void addTopics() {
        CourseDto emptyCourseDto = new CourseDto();

        when(courseDao.addTopics(courseDto)).thenReturn(true);
        when(courseDao.addTopics(emptyCourseDto)).thenReturn(false);

        assertTrue(service.addTopics(courseDto));
        assertFalse(service.addTopics(emptyCourseDto));
    }

    @Test
    void getAll() {
        when(courseDao.getAll()).thenReturn(courseDtoList);
        assertEquals(1, service.getAll().size());
    }

    @Test
    void getAllByUserId() {
        when(courseDao.getAllByUserId(10)).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByUserId(10).size());
    }

    @Test
    void getAllByStatus() {
        when(courseDao.getAllByStatus("FINISHED")).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByStatus("FINISHED").size());
    }

    @Test
    void getAllByAlphabet() {
        when(courseDao.getAllByAlphabet("az-za")).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByAlphabet("az-za").size());
    }

    @Test
    void getAllByDuration() {
        when(courseDao.getAllByDuration("MIN-MAX")).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByDuration("MIN-MAX").size());
    }

    @Test
    void getAllByName() {
        when(courseDao.getAllByName("Kotlin")).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByName("Kotlin").size());
    }

    @Test
    void getAllByTopic() {
        when(courseDao.getAllByTopic("BBC")).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByTopic("BBC").size());
    }

    @Test
    void getAllByDateStart() {
        Date date = new Date(1000);
        when(courseDao.getAllByDateStart(date)).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByDateStart(date).size());
    }

    @Test
    void getAllByStatusUserId() {
        when(courseDao.getAllByStatusUserId("CURRENT", 5)).thenReturn(courseDtoList);
        assertEquals(1, service.getAllByStatusUserId("CURRENT", 5).size());
    }

    @Test
    void getAllStudentsWithGradesByCourseId() {
        when(courseDao.getAllStudentsWithGradesByCourseId(10)).thenReturn(Collections.singletonList(new UserDto()));
        assertEquals(1, service.getAllStudentsWithGradesByCourseId(10).size());
    }

    @Test
    void getCourseById() {
        when(courseDao.getCourseById(10)).thenReturn(courseDto);
        when(courseDao.getCourseById(-10)).thenReturn(null);

        assertNotNull(service.getCourseById(10));
        assertNull(service.getCourseById(-10));
    }
}