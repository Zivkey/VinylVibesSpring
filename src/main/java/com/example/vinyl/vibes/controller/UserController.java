package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.UserDTO;
import com.example.vinyl.vibes.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("public/register")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        return userServiceImpl.create(userDTO);
    }

    @PostMapping("public/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return userServiceImpl.login(userDTO);
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        return userServiceImpl.update(userDTO);
    }

}
