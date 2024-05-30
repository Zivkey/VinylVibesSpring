package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.UserDTO;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.UserRepository;
import com.example.vinyl.vibes.tools.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    public ResponseEntity<?> create(UserDTO userDTO) {
        try {
            User user = userDTO.toEntity();
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("User with that email already exists", HttpStatus.BAD_REQUEST);
            }
            if (!userDTO.getEmail().isEmpty()) {
                if (!userDTO.getEmail().contains("@gmail.com")) {
                    return new ResponseEntity<>("Your email needs to have @gmail.com", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(UserDTO dto) {
        try {
            Optional<User> optionalUser = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
            }
            UserDTO userDTO = optionalUser.get().toDTO();
            log.info(JwtTool.generateToken(optionalUser.get().getEmail()));
            userDTO.setJwt(JwtTool.generateToken(optionalUser.get().getEmail()));
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAll() {
        try {
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(users.stream().map(User::toDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> update(UserDTO userDTO) {
        try {
            User user = (User) request.getAttribute("USER");
            if (user.getAdmin().equals(true) && userDTO.getId() != null) {
                Optional<User> optionalUser = userRepository.findById(userDTO.getId());
                if (optionalUser.isEmpty()) {
                    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }
                user = optionalUser.get();
            }
            user = user.update(userDTO);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
