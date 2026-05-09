package com.reviewsite.reviewsite_api.repository;

import com.reviewsite.reviewsite_api.entity.Show;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShowRepository extends MongoRepository<Show, String> {
    List<Show> findByTitle(String title);

}
