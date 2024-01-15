package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.UserDTO;
import com.example.vinyl.vibes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

}
