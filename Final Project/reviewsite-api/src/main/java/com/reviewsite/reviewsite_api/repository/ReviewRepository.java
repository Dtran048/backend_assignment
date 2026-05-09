package com.reviewsite.reviewsite_api.repository;

import com.reviewsite.reviewsite_api.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {

    List<Review> findByUserId(String userId);


}
