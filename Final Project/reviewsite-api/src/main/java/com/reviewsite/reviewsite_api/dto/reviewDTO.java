package com.reviewsite.reviewsite_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class reviewDTO {
    private String Id;
    private String show_name;
    private String username;
    private String comment;
    private Integer rating;
}
