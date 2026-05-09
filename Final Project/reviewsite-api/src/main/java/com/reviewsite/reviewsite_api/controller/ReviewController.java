package com.reviewsite.reviewsite_api.controller;

import com.reviewsite.reviewsite_api.entity.Review;
import com.reviewsite.reviewsite_api.entity.User;
import com.reviewsite.reviewsite_api.repository.ReviewRepository;
import com.reviewsite.reviewsite_api.repository.UserRepository;
import com.reviewsite.reviewsite_api.service.ReviewService;
import com.reviewsite.reviewsite_api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/{id}")
    public ResponseEntity<?> createReview(
            @RequestBody Review review,
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader){
        final String jwt = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwt);
        review.setUserId(userRepo.findByUsername(username).get().getId());
        if(review.getRating() == null){
            return ResponseEntity.status(400)
                    .body(Map.of("status", "400", "message", "Missing rating field","timestamps", new Date()));
        }
        review.setShowId(id);
        return ResponseEntity.status(201).body(reviewService.createReview(review));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authHeader){
        final String jwt = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwt);
        final String userId = userRepo.findByUsername(username).get().getId();
        System.out.println(userId);

        return ResponseEntity.status(200).body(reviewService.getAllReviews(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getreview(@PathVariable String id, @RequestHeader("Authorization") String authHeader){
        if (reviewRepo.findById(id).isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "400", "message", "review not found","timestamps", new Date()));
        }
        final String jwt = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwt);
        User saved = userRepo.findByUsername(username).orElse(null);
        if(!reviewRepo.findById(id).get().getUserId().equals(saved.getId()))  {
            return ResponseEntity.status(403)
                    .body(Map.of("status", "403", "message", "User not own this review","timestamps", new Date()));
        }
        return ResponseEntity.status(200).body(reviewService.getReview(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateReview(
            @RequestBody Review review,
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader){
        Review saved = reviewRepo.findById(id).orElse(null);
        if (saved == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("status", "404", "message", "review not found","timestamps", new Date()));
        }
        final String jwt = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwt);
        User savedUser = userRepo.findByUsername(username).orElse(null);
        if (savedUser.getId().equals(saved.getUserId())) {
            if (review.getComment() != null){
                saved.setComment(review.getComment());
            }
            if (review.getRating() != null) {
                saved.setRating(review.getRating());
            }
        }else  {
            return ResponseEntity.status(403)
                    .body(Map.of("status", "403", "message", "User not own this review","timestamps", new Date()));
        }
        return ResponseEntity.status(200).body(reviewService.updateReview(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader){
        Review saved = reviewRepo.findById(id).orElse(null);
        final String jwt = authHeader.substring(7);
        final String username = jwtUtil.extractUsername(jwt);
        User savedUser = userRepo.findByUsername(username).orElse(null);
        if(saved == null){
            return ResponseEntity.status(404)
                    .body(Map.of("status", "404", "message", "Review not found","timestamps", new Date()));
        } else if (savedUser.getId().equals(saved.getUserId())) {
            reviewService.deleteReview(id);
            return ResponseEntity.status(200)
                    .body(Map.of("status", "200", "message", "Review deleted successfully","timestamps", new Date()));
        }else  {
            return ResponseEntity.status(403)
                    .body(Map.of("status", "403", "message", "User not own this review","timestamps", new Date()));
        }
    }

}
