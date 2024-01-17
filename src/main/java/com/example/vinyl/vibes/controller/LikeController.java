package com.example.vinyl.vibes.controller;
import com.example.vinyl.vibes.dto.LikeDTO;
import com.example.vinyl.vibes.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody LikeDTO likeDTO) {
        return likeService.create(likeDTO);
    }

    @PostMapping("delete")
    public ResponseEntity<?> remove(@RequestBody LikeDTO likeDTO) {
        return likeService.remove(likeDTO);
    }

    @GetMapping()
    public ResponseEntity<?> isLiked(@RequestParam String albumId, @RequestParam String userId) {
        return likeService.isLiked(albumId, userId);
    }
}
