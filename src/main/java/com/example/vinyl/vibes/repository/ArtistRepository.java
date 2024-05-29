package com.example.vinyl.vibes.repository;

import com.example.vinyl.vibes.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, String> {
    @Query("select a from Artist a where a.name = ?1")
    Optional<Artist> findByName(String artist);
}
