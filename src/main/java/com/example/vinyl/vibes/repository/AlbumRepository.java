package com.example.vinyl.vibes.repository;

import com.example.vinyl.vibes.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {

    @Query("select a from Album a where a.deleted = false")
    public List<Album> findAllByDeletedFalse();

}
