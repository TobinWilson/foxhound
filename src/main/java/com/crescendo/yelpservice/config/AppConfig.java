package com.crescendo.yelpservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("application.request")
public class AppConfig {

    private String awsRegion;

    private String s3Bucket;

    private List<String> s3Objects;

    private String requestMethod;

    private Integer requestTimeout;

    private String baseUrl;

    private String endpoint;

    private String businessId;

    private String authKey;

    private String authValue;

}
