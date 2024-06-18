package com.example.vinyl.vibes;

import com.example.vinyl.vibes.dto.UserDTO;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.UserRepository;
import com.example.vinyl.vibes.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userServiceImpl.create(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");

        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userServiceImpl.create(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User with that email already exists", response.getBody());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("password");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userServiceImpl.login(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }

    @Test
    void testLogin_Failure() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("password");

        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = userServiceImpl.login(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Login failed", response.getBody());
        verify(userRepository, times(1)).findByEmailAndPassword(anyString(), anyString());
    }

    @Test
    void testGetAllUsers_Success() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<?> response = userServiceImpl.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_Exception() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = userServiceImpl.getAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");

        User user = new User();
        user.setId("1");
        user.setAdmin(true);

        when(request.getAttribute("USER")).thenReturn(user);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userServiceImpl.update(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");

        User user = new User();
        user.setId("1");
        user.setAdmin(true);

        when(request.getAttribute("USER")).thenReturn(user);
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = userServiceImpl.update(userDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Exception() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");

        User user = new User();
        user.setId("1");
        user.setAdmin(true);

        when(request.getAttribute("USER")).thenReturn(user);
        when(userRepository.findById(anyString())).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = userServiceImpl.update(userDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(0)).save(any(User.class));
    }

}
