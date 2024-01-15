package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.UserDTO;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> create(UserDTO userDTO) {
        try {
            User user = userDTO.toEntity();
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("User with that email already exists", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(UserDTO userDTO) {
        try {
            Optional<User> optionalUser = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(optionalUser.get().toDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> update(UserDTO userDTO) {
        try {
            Optional<User> optionalUser = userRepository.findById(userDTO.getId());
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            User user = optionalUser.get().update(userDTO);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
