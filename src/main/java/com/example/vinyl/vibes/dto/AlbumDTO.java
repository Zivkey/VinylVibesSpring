package com.example.vinyl.vibes.dto;

import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private String id;
    private String name;
    private String image;
    private String description;
    private String artist;
    private Integer year;
    private Genre genre;
    private Integer likes;
    private Integer dislikes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Album toEntity() {
        return Album.builder()
                .name(this.name)
                .image(this.image)
                .year(this.year)
                .description(this.description)
                .genre(this.genre)
                .artist(this.artist)
                .build();
    }
}
