package com.example.vinyl.vibes;

import com.example.vinyl.vibes.dto.ReviewsDTO;
import com.example.vinyl.vibes.entity.Album;
import com.example.vinyl.vibes.entity.Review;
import com.example.vinyl.vibes.entity.User;
import com.example.vinyl.vibes.repository.AlbumRepository;
import com.example.vinyl.vibes.repository.ReviewRepository;
import com.example.vinyl.vibes.service.impl.ReviewServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview_Success() {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        Album album = new Album();
        album.setId("1");

        Review review = new Review();
        review.setUser(user);
        review.setAlbum(album);

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ResponseEntity<?> response = reviewServiceImpl.createReview(reviewsDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testCreateReview_AlbumNotFound() {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewServiceImpl.createReview(reviewsDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Album not found", response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void testCreateReview_Exception() {
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setAlbumId("1");

        User user = new User();
        user.setId("1");

        when(request.getAttribute("USER")).thenReturn(user);
        when(albumRepository.findById(anyString())).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = reviewServiceImpl.createReview(reviewsDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(0)).save(any(Review.class));
    }

    @Test
    void testGetAllReviews_Success() {
        Album album = new Album();
        album.setId("1");

        Review review1 = new Review();
        Review review2 = new Review();
        List<Review> reviews = List.of(review1, review2);

        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(reviewRepository.findAllByAlbumId(anyString())).thenReturn(reviews);

        ResponseEntity<?> response = reviewServiceImpl.getAllReviews("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(1)).findAllByAlbumId(anyString());
    }

    @Test
    void testGetAllReviews_AlbumNotFound() {
        when(albumRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = reviewServiceImpl.getAllReviews("1");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Album not found", response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(0)).findAllByAlbumId(anyString());
    }

    @Test
    void testGetAllReviews_NoReviews() {
        Album album = new Album();
        album.setId("1");

        when(albumRepository.findById(anyString())).thenReturn(Optional.of(album));
        when(reviewRepository.findAllByAlbumId(anyString())).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = reviewServiceImpl.getAllReviews("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ArrayList<>(), response.getBody());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(1)).findAllByAlbumId(anyString());
    }

    @Test
    void testGetAllReviews_Exception() {
        when(albumRepository.findById(anyString())).thenThrow(new RuntimeException("Database Error"));

        ResponseEntity<?> response = reviewServiceImpl.getAllReviews("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(albumRepository, times(1)).findById(anyString());
        verify(reviewRepository, times(0)).findAllByAlbumId(anyString());
    }

}
