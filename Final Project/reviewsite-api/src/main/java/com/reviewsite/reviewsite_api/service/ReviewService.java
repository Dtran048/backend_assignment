package com.reviewsite.reviewsite_api.service;

import com.reviewsite.reviewsite_api.dto.reviewDTO;
import com.reviewsite.reviewsite_api.entity.Review;
import com.reviewsite.reviewsite_api.entity.Show;
import com.reviewsite.reviewsite_api.entity.User;
import com.reviewsite.reviewsite_api.repository.ReviewRepository;
import com.reviewsite.reviewsite_api.repository.ShowRepository;
import com.reviewsite.reviewsite_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ShowRepository showRepo;

    @Autowired
    private UserRepository userRepo;

    public reviewDTO createReview(Review review) {
        Review saved = reviewRepo.save(review);
        Show show =  showRepo.findById(review.getShowId()).orElse(null);
        User user = userRepo.findById(review.getUserId()).orElse(null);
        return new reviewDTO(
                saved.getId(),
                show.getTitle(),
                user.getUsername(),
                saved.getComment(),
                saved.getRating()
        );
    }

    public List<reviewDTO> getAllReviews(String userId) {
        return reviewRepo.findByUserId(userId).stream().map(
            review -> new reviewDTO(
                    review.getId(),
                    showRepo.findById(review.getShowId()).get().getTitle(),
                    userRepo.findById(review.getUserId()).get().getUsername(),
                    review.getComment(),
                    review.getRating()
            )
        ).collect(Collectors.toList());


    }

    public reviewDTO getReview(String id) {
        Review review = reviewRepo.findById(id).orElse(null);;
        Show show =  showRepo.findById(review.getShowId()).orElse(null);
        User user = userRepo.findById(review.getUserId()).orElse(null);
        return new reviewDTO(
                review.getId(),
                show.getTitle(),
                user.getUsername(),
                review.getComment(),
                review.getRating()

        );
    }

    public Review updateReview(Review review) {
        return reviewRepo.save(review);
    }

    public void deleteReview(String id) {
        reviewRepo.deleteById(id);
    }

}
