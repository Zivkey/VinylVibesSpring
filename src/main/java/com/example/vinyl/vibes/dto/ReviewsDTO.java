package com.example.vinyl.vibes.dto;

import com.example.vinyl.vibes.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDTO {
    private String id;
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String albumId;
    private AlbumDTO album;
    private String title;
    private String reviewText;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review toEntity() {
        return Review.builder()
                .title(this.title)
                .reviewsText(this.reviewText)
                .build();
    }

}
