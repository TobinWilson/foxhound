package com.crescendo.yelpservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserEntity {

    private String id;

    @JsonProperty("profile_url")
    private String profileUrl;

    @JsonProperty("image_url")
    private String imageUrl;

    private String name;

}
