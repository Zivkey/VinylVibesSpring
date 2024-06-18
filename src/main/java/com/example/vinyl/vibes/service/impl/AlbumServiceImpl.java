package com.example.vinyl.vibes.service.impl;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Artist;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ArtistRepository;
import com.example.vinyl.vibes.service.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;
    private final HttpServletRequest request;

    @Override
    public ResponseEntity<?> create(AlbumDTO albumDTO) {
        try {
            Album album = albumDTO.toEntity();
            User user = (User) request.getAttribute("USER");
            if (!user.getAdmin()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Optional<Artist> optionalArtist = artistRepository.findByName(albumDTO.getArtist());
            if (optionalArtist.isPresent()) {
                album.setArtist(optionalArtist.get());
            } else {
                Artist artist = artistRepository.save(Artist.builder()
                        .name(albumDTO.getArtist())
                        .build());
                album.setArtist(artist);
            }

            album.setLikes(0);
            album.setDislikes(0);
            return new ResponseEntity<>(albumRepository.save(album), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<Album> albums = albumRepository.findAll();
            return new ResponseEntity<>(albums.stream().map(Album::toDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> delete(String id) {
        try {
            Optional<Album> optionalAlbum = albumRepository.findById(id);
            optionalAlbum.ifPresent(album -> album.setDeleted(true));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
