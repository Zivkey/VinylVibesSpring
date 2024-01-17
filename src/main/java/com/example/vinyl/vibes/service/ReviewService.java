package com.example.vinyl.vibes.service;

import com.example.vinyl.vibes.dto.ReviewsDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Review;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ReviewRepository;
import com.example.vinyl.vibes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AlbumRepository albumRepository;

    public ResponseEntity<?> createReview(ReviewsDTO reviewsDTO) {
        try {
            Optional<Album> optionalAlbum = albumRepository.findById(reviewsDTO.getAlbumId());
            if (optionalAlbum.isEmpty()) {
                return new ResponseEntity<>("Album not found", HttpStatus.BAD_REQUEST);
            }
            Optional<User> optionalUser = userRepository.findById(reviewsDTO.getUserId());
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            Review review = reviewsDTO.toEntity();
            review.setUser(optionalUser.get());
            review.setAlbum(optionalAlbum.get());
            return new ResponseEntity<>(reviewRepository.save(review).toDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllReviews(String albumId) {
        try {
            Optional<Album> optionalAlbum = albumRepository.findById(albumId);
            if (optionalAlbum.isEmpty()) {
                return new ResponseEntity<>("Album not found", HttpStatus.BAD_REQUEST);
            }
            List<Review> reviewList = reviewRepository.findAllByAlbumId(albumId);
            if (!reviewList.isEmpty()) {
                return new ResponseEntity<>(reviewList.stream().map(Review::toDTO), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
