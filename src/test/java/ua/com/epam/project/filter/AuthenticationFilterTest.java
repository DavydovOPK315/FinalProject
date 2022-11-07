package ua.com.epam.project.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.epam.project.dto.UserDto;
import ua.com.epam.project.entity.User;
import ua.com.epam.project.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static util.SetFinalStatic.setFinalStatic;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Test
    void doFilterSomeStatic() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());
        when(request.getServletPath()).thenReturn("/static/css");
        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
    }

    @Test
    void doFilterIfUserNullWithGuestEndpoint() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getServletPath()).thenReturn("/login");
        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
    }

    @Test
    void doFilterIfUserNullWithoutGuestEndpoint() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getServletPath()).thenReturn("/elective/courses");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(request, times(1)).getRequestDispatcher("/login.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilterIfAdmin() throws ServletException, IOException {
        User user = new User();
        user.setRoleId(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/elective/courses");

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterIfStudentOrTeacher() throws ServletException, IOException {
        User user = new User();
        user.setRoleId(2);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/locale");

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterIfStudentOrTeacherOnAdminPage() throws ServletException, IOException {
        User user = new User();
        user.setRoleId(2);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/admin");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(requestDispatcher, times(1)).forward(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilterIfStudentOrTeacherIsBanned() throws Exception {
        User user = new User();
        user.setId(10);
        user.setRoleId(2);
        UserDto userDto = new UserDto();
        userDto.setStatus("BANNED");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/elective");
        when(userService.getUserById(user.getId())).thenReturn(userDto);
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);

        setFinalStatic(AuthenticationFilter.class.getDeclaredField("userService"), userService);

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(requestDispatcher, times(1)).forward(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilterIfStudentOrTeacherIsActive() throws Exception {
        User user = new User();
        user.setId(10);
        user.setLogin("user");
        user.setFirstName("fName");
        user.setLastName("lName");
        user.setEmail("email");
        user.setRoleId(2);
        UserDto userDto = new UserDto();
        userDto.setLogin("user");
        userDto.setFirstName("fName");
        userDto.setLastName("lName");
        userDto.setEmail("email");
        userDto.setStatus("ACTIVE");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/elective");
        when(userService.getUserById(user.getId())).thenReturn(userDto);

        setFinalStatic(AuthenticationFilter.class.getDeclaredField("userService"), userService);

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(request, response);
        verify(requestDispatcher, never()).forward(request, response);
    }

    @Test
    void doFilterIfStudentOrTeacherIsActiveButDifferent() throws Exception {
        User user = new User();
        user.setId(10);
        user.setLogin("user");
        user.setFirstName("fName");
        user.setLastName("lName");
        user.setEmail("email");
        user.setRoleId(2);
        UserDto userDto = new UserDto();
        userDto.setLogin("userDto");
        userDto.setStatus("ACTIVE");
        userDto.setEmail("email");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getServletPath()).thenReturn("/elective");
        when(userService.getUserById(user.getId())).thenReturn(userDto);
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        setFinalStatic(AuthenticationFilter.class.getDeclaredField("userService"), userService);

        assertDoesNotThrow(() -> authenticationFilter.doFilter(request, response, filterChain));
        verify(filterChain, times(1)).doFilter(request, response);
        verify(requestDispatcher, never()).forward(request, response);
    }
}