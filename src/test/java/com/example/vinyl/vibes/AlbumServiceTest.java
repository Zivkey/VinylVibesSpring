package com.example.vinyl.vibes;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Artist;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ArtistRepository;
import com.example.vinyl.vibes.service.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AlbumServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAlbum_Success() {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setArtist("Test Artist");

        User user = new User();
        user.setAdmin(true);

        when(request.getAttribute("USER")).thenReturn(user);
        when(artistRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(artistRepository.save(any(Artist.class))).thenReturn(new Artist());
        when(albumRepository.save(any(Album.class))).thenReturn(new Album());

        ResponseEntity<?> response = albumService.create(albumDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(artistRepository, times(1)).findByName(anyString());
        verify(artistRepository, times(1)).save(any(Artist.class));
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    void testCreateAlbum_Unauthorized() {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setArtist("Test Artist");

        User user = new User();
        user.setAdmin(false);

        when(request.getAttribute("USER")).thenReturn(user);

        ResponseEntity<?> response = albumService.create(albumDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(artistRepository, times(0)).findByName(anyString());
        verify(artistRepository, times(0)).save(any(Artist.class));
        verify(albumRepository, times(0)).save(any(Album.class));
    }

    @Test
    void testCreateAlbum_Exception() {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setArtist("Test Artist");

        User user = new User();
        user.setAdmin(true);

        when(request.getAttribute("USER")).thenReturn(user);
        when(artistRepository.findByName(anyString())).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = albumService.create(albumDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(artistRepository, times(1)).findByName(anyString());
    }

    @Test
    void testGetAllAlbums_Success() {
        Album album1 = new Album();
        Album album2 = new Album();

        List<Album> albums = new ArrayList<>();

        albums.add(album1);
        albums.add(album2);

        when(albumRepository.findAll()).thenReturn(albums);

        ResponseEntity<?> response = albumService.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findAll();
    }

    @Test
    void testGetAllAlbums_Exception() {
        when(albumRepository.findAll()).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = albumService.getAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(albumRepository, times(1)).findAll();
    }

}
