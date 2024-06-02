package com.example.vinyl.vibes.migration;

import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Artist;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.enums.Genre;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ArtistRepository;
import com.example.vinyl.vibes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class V1__init__data {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public void migrate() throws Exception {
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(User.builder().admin(false).email("user@gmail.com").firstName("Pera").
                    lastName("Peric").password("pera123").build());
            userRepository.save(User.builder().admin(true).email("admin@gmail.com").firstName("Super").
                    lastName("Admin").password("admin123").build());
        }

        Optional<Artist> artist1 = artistRepository.findByName("J. Cole");
        if (artist1.isEmpty()) {
            artist1 = Optional.of(artistRepository.save(Artist.builder()
                    .name("J. Cole")
                    .build()));
        }
        Optional<Artist> artist2 = artistRepository.findByName("Nirvana");
        if (artist2.isEmpty()) {
            artist2 = Optional.of(artistRepository.save(Artist.builder()
                    .name("Nirvana")
                    .build()));
        }
        Optional<Artist> artist3 = artistRepository.findByName("Kanye West");
        if (artist3.isEmpty()) {
            artist3 = Optional.of(artistRepository.save(Artist.builder()
                    .name("Kanye West")
                    .build()));
        }


        if (albumRepository.findAll().isEmpty()) {
            albumRepository.save(Album.builder().name("2014 Forrest Hills Drive").
                    image("https://m.media-amazon.com/images/I/71v0b7YG+zL._UF1000,1000_QL80_.jpg")
                    .year(2014).artist(artist1.get()).genre(Genre.HIP_HOP).
                    description("In 2014, Cole bought the house, and was the first home he had purchased. The album's title is used to recount " +
                            "Cole's upbringing, leaving North Carolina to New York;").dislikes(14).likes(102).build());
            albumRepository.save(Album.builder().name("Nevermind").
                    image("https://images.prismic.io/milanote/df7eeb83a07162b45ac2e882cac055de9411054a_cover.jpg?auto=compress,format")
                    .year(1991).artist(artist2.get()).genre(Genre.ROCK).
                    description("It includes anti-establishment views, anti-sexism, frustration, alienation, and troubled love inspired by Cobain's " +
                            "broken relationship with Bikini Kill's Tobi Vail.").dislikes(55).likes(221).build());
            albumRepository.save(Album.builder().name("Graduation").
                    image("https://m.media-amazon.com/images/I/71T4fEX7jaL._AC_UF1000,1000_QL80_.jpg")
                    .year(2007).artist(artist3.get()).genre(Genre.HIP_HOP).
                    description("GRADUATION is the 3rd installment in the Kanye West series of ground breaking albums, targeting every school kid," +
                            " from those that have dropped out of collage.").dislikes(21).likes(341).build());
        }
    }
}
