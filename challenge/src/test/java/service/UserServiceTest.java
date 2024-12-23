package service;

import com.globallogic.challenge.api.dao.User;
import com.globallogic.challenge.api.dto.UserDto;
import com.globallogic.challenge.api.repository.UserRepository;
import com.globallogic.challenge.api.services.TokenService;
import com.globallogic.challenge.api.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpSuccess() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("A1validpass");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.signUp(userDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignUpUserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("A1validpass");

        User existingUser = new User();
        existingUser.setEmail(userDto.getEmail());

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));

        ResponseEntity<?> response = userService.signUp(userDto);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User already exists", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setToken("mockedValidToken");
        user.setLastLogin(LocalDateTime.now());

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tokenService.generateToken(user.getEmail())).thenReturn("validToken");
        when(tokenService.parseToken("validToken")).thenReturn(user.getEmail());

        String token = tokenService.generateToken(user.getEmail());
        ResponseEntity<?> response = userService.login(token);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginInvalidToken() {
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWtlQGV4YW1wbGUuY29tIiwiZXhwIjoxNjg1NTM2MDAwfQ.invalidSignature";

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = userService.login(invalidToken);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid token", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }
}
