package com.crescendo.yelpservice.entity;

import com.crescendo.yelpservice.common.contracts.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewEntity implements Review {

    private String id;

    private String url;

    private String text;

    private Integer rating;

    @JsonProperty("time_created")
    private String timeCreated;

    private UserEntity user;

}
