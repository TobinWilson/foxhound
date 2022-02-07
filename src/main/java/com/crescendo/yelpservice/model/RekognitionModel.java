package com.crescendo.yelpservice.model;

import com.crescendo.yelpservice.common.contracts.Review;
import com.crescendo.yelpservice.entity.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RekognitionModel implements Review {

    public RekognitionModel() {
        this.labels = new ArrayList<>();
    }

    private String id;

    private String url;

    private String text;

    private Integer rating;

    private String timeCreated;

    private UserEntity user;

    private List<String> labels;

    public void map(Review review) {
        this.id = review.getId();
        this.url = review.getUrl();
        this.text = review.getText();
        this.rating = review.getRating();
        this.timeCreated = review.getTimeCreated();
        this.user = review.getUser();
    }

}
