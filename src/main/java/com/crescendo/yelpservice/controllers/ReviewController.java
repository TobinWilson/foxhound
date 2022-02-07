package com.crescendo.yelpservice.controllers;

import com.crescendo.yelpservice.model.RekognitionModel;
import com.crescendo.yelpservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public List<RekognitionModel> getReviews() throws Exception {
        return reviewService.getReviews();
    }

}
