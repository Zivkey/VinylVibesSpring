package com.example.vinyl.vibes;

import com.example.vinyl.vibes.dto.LikeDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Like;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.LikeRepository;
import com.example.vinyl.vibes.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LikeServiceTest {


    @Mock
    private LikeRepository likeRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLike_Success() {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setAlbumId("1");
        likeDTO.setValue(true);

        User user = new User();
        user.setId("1");

        Album album = new Album();
        album.setId("1");
        album.setLikes(0);
        album.setDislikes(0);

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(likeRepository.findByAlbumIdAndUserId(anyString(), anyString())).thenReturn(Optional.empty());
        when(likeRepository.save(any(Like.class))).thenReturn(new Like());
        when(albumRepository.save(any(Album.class))).thenReturn(album);

        ResponseEntity<?> response = likeService.create(likeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(1)).findByAlbumIdAndUserId(anyString(), anyString());
        verify(likeRepository, times(1)).save(any(Like.class));
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    void testCreateLike_AlbumNotFound() {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = likeService.create(likeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Album not found", response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(0)).findByAlbumIdAndUserId(anyString(), anyString());
        verify(likeRepository, times(0)).save(any(Like.class));
        verify(albumRepository, times(0)).save(any(Album.class));
    }

    @Test
    void testRemoveLike_Success() {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        Album album = new Album();
        album.setId("1");

        Like like = new Like();
        like.setAlbum(album);
        like.setUser(user);

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(likeRepository.findByAlbumIdAndUserId(anyString(), anyString())).thenReturn(Optional.of(like));

        ResponseEntity<?> response = likeService.remove(likeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(1)).findByAlbumIdAndUserId(anyString(), anyString());
        verify(likeRepository, times(1)).delete(any(Like.class));
    }

    @Test
    void testRemoveLike_AlbumNotFound() {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = likeService.remove(likeDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Album not found", response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(0)).findByAlbumIdAndUserId(anyString(), anyString());
        verify(likeRepository, times(0)).delete(any(Like.class));
    }

    @Test
    void testIsLiked_Success() {
        User user = new User();
        user.setId("1");

        Album album = new Album();
        album.setId("1");

        Like like = new Like();
        like.setAlbum(album);
        like.setUser(user);

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(likeRepository.findByAlbumIdAndUserId(anyString(), anyString())).thenReturn(Optional.of(like));

        ResponseEntity<?> response = likeService.isLiked("1", "1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(1)).findByAlbumIdAndUserId(anyString(), anyString());
    }

    @Test
    void testIsLiked_AlbumNotFound() {
        User user = new User();
        user.setId("1");

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = likeService.isLiked("1", "1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Album not found", response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(likeRepository, times(0)).findByAlbumIdAndUserId(anyString(), anyString());
    }

}
