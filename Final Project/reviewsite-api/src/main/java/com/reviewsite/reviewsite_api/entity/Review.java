package com.reviewsite.reviewsite_api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String userId;
    private Integer rating;
    private String comment;
    private String showId;
}


