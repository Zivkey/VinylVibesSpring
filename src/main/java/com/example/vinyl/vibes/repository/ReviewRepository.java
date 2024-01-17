package com.example.vinyl.vibes.repository;

import com.example.vinyl.vibes.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("select r from Review r where r.album.id = ?1")
    List<Review> findAllByAlbumId(String albumId);
}
