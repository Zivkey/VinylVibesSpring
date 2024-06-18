package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.LikeDTO;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<?> create(LikeDTO likeDTO);

    ResponseEntity<?> remove(LikeDTO likeDTO);

    ResponseEntity<?> isLiked(String albumId, String userId);
}
