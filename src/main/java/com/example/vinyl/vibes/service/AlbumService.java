package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.AlbumDTO;
import org.springframework.http.ResponseEntity;

public interface AlbumService {
    ResponseEntity<?> create(AlbumDTO albumDTO);

    ResponseEntity<?> getAll();
}
