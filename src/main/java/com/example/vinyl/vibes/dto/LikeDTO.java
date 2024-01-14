package com.example.vinyl.vibes.dto;

import com.example.vinyl.vibes.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private String id;
    private UserDTO user;
    private String userId;
    private AlbumDTO album;
    private String albumId;
    private Boolean value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Like toEntity() {
        return Like.builder()
                .value(this.value)
                .build();
    }
}
