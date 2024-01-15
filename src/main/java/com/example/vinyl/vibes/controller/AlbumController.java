package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AlbumDTO albumDTO) {
        return albumService.create(albumDTO);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return albumService.getAll();
    }
}
