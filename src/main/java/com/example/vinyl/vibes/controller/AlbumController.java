package com.example.vinyl.vibes.controller;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.service.impl.AlbumServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumServiceImpl albumServiceImpl;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AlbumDTO albumDTO) {
        return albumServiceImpl.create(albumDTO);
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return albumServiceImpl.getAll();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return albumServiceImpl.delete(id);
    }
}
