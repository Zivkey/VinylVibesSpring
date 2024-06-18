package com.example.vinyl.vibes.controller;
import com.example.vinyl.vibes.dto.LikeDTO;
import com.example.vinyl.vibes.service.impl.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeServiceImpl;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody LikeDTO likeDTO) {
        return likeServiceImpl.create(likeDTO);
    }

    @PostMapping("delete")
    public ResponseEntity<?> remove(@RequestBody LikeDTO likeDTO) {
        return likeServiceImpl.remove(likeDTO);
    }

    @GetMapping()
    public ResponseEntity<?> isLiked(@RequestParam String albumId, @RequestParam String userId) {
        return likeServiceImpl.isLiked(albumId, userId);
    }
}
