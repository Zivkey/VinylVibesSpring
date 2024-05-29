package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.AlbumDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Artist;
import com.example.vinyl.vibes.entity.Like;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ArtistRepository;
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
public class AlbumService {
    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    public ResponseEntity<?> create(AlbumDTO albumDTO) {
        try {
            Album album = albumDTO.toEntity();

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

    public ResponseEntity<?> getAll() {
        try {
            List<Album> albums = albumRepository.findAll();
            return new ResponseEntity<>(albums.stream().map(Album::toDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
