package com.crescendo.yelpservice.model;

import com.crescendo.yelpservice.entity.ReviewEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReviewModel {

    private List<ReviewEntity> reviews;

    @JsonProperty("total")
    private Integer totalReviews;

    @JsonProperty("possible_languages")
    private List<String> possibleLanguages;

}
