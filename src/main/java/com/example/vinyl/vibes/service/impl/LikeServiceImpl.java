package com.example.vinyl.vibes.service.impl;

import com.example.vinyl.vibes.dto.LikeDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Like;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.LikeRepository;
import com.example.vinyl.vibes.repository.UserRepository;
import com.example.vinyl.vibes.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<?> create(LikeDTO likeDTO) {
        try {
            User user = (User) request.getAttribute("USER");
            Optional<Album> optionalAlbum = albumRepository.findById(likeDTO.getAlbumId());
            if (optionalAlbum.isEmpty()) {
                return new ResponseEntity<>("Album not found", HttpStatus.BAD_REQUEST);
            }
            Optional<Like> optionalLike = likeRepository.findByAlbumIdAndUserId(optionalAlbum.get().getId(), user.getId());
            if (optionalLike.isPresent()) {
                if (!optionalLike.get().getValue().equals(likeDTO.getValue())) {
                    optionalLike.get().setUser(null);
                    optionalLike.get().setAlbum(null);
                    if (optionalLike.get().getValue()) {
                        optionalAlbum.get().setLikes(optionalAlbum.get().getLikes() - 1);
                    } else {
                        optionalAlbum.get().setDislikes(optionalAlbum.get().getDislikes() - 1);
                    }
                    likeRepository.delete(optionalLike.get());
                } else {
                    optionalLike.get().setUser(null);
                    optionalLike.get().setAlbum(null);
                    likeRepository.delete(optionalLike.get());
                    if (optionalLike.get().getValue()) {
                        optionalAlbum.get().setLikes(optionalAlbum.get().getLikes() - 1);
                        albumRepository.save(optionalAlbum.get());
                    } else {
                        optionalAlbum.get().setDislikes(optionalAlbum.get().getDislikes() - 1);
                        albumRepository.save(optionalAlbum.get());
                    }

                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            Like like = Like.builder()
                    .album(optionalAlbum.get())
                    .user(user)
                    .value(likeDTO.getValue())
                    .build();
            likeRepository.save(like);
            if (likeDTO.getValue()) {
                optionalAlbum.get().setLikes(optionalAlbum.get().getLikes() + 1);
            } else {
                optionalAlbum.get().setDislikes(optionalAlbum.get().getDislikes() + 1);
            }
            albumRepository.save(optionalAlbum.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> remove(LikeDTO likeDTO) {
        try {
            User user = (User) request.getAttribute("USER");
            Optional<Album> optionalAlbum = albumRepository.findById(likeDTO.getAlbumId());
            if (optionalAlbum.isEmpty()) {
                return new ResponseEntity<>("Album not found", HttpStatus.BAD_REQUEST);
            }
            Optional<Like> optionalLike = likeRepository.findByAlbumIdAndUserId(optionalAlbum.get().getId(), user.getId());
            if (optionalLike.isPresent()) {
                optionalLike.get().setAlbum(null);
                optionalLike.get().setUser(null);
                likeRepository.delete(optionalLike.get());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> isLiked(String albumId, String userId) {
        try {
            User user = (User) request.getAttribute("USER");
            Optional<Album> optionalAlbum = albumRepository.findById(albumId);
            if (optionalAlbum.isEmpty()) {
                return new ResponseEntity<>("Album not found", HttpStatus.BAD_REQUEST);
            }
            Optional<Like> optionalLike = likeRepository.findByAlbumIdAndUserId(albumId, user.getId());
            if (optionalLike.isPresent()) {
                return new ResponseEntity<>(optionalLike.get().toDTO(), HttpStatus.OK);
            } else {
                Like like = Like.builder().build();
                return new ResponseEntity<>(like, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
