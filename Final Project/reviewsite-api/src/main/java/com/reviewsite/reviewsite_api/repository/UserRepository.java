package com.reviewsite.reviewsite_api.repository;

import com.reviewsite.reviewsite_api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);


}
