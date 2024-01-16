package com.example.vinyl.vibes.repository;

import com.example.vinyl.vibes.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    @Query("select l from Like l where l.album.id = ?1 and l.user.id = ?2")
    Optional<Like> findByAlbumIdAndUserId(String albumId, String userId);
}
