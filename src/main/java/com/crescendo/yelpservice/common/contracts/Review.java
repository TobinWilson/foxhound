package com.crescendo.yelpservice.common.contracts;

import com.crescendo.yelpservice.entity.UserEntity;

public interface Review {

    String getId();

    void setId(String id);

    String getUrl();

    void setUrl(String url);

    String getText();

    void setText(String text);

    Integer getRating();

    void setRating(Integer rating);

    String getTimeCreated();

    void setTimeCreated(String timeCreated);

    UserEntity getUser();

    void setUser(UserEntity user);

}
