package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> create(UserDTO userDTO);

    ResponseEntity<?> login(UserDTO dto);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(UserDTO userDTO);
}
