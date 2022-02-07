package com.crescendo.yelpservice.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.crescendo.yelpservice.common.contracts.Review;
import com.crescendo.yelpservice.config.AppConfig;
import com.crescendo.yelpservice.entity.ReviewEntity;
import com.crescendo.yelpservice.handler.ApiRequestHandler;
import com.crescendo.yelpservice.model.RekognitionModel;
import com.crescendo.yelpservice.model.ReviewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReviewService {

    @Autowired
    private AppConfig config;

    @Autowired
    private ApiRequestHandler apiRequestHandler;

    private AmazonRekognition rekognitionClient;

    /**
     * Return a list of reviews associated with the given Yelp business ID.
     * Yelp API limits results to 3 reviews per request.
     *
     * @return
     * @throws Exception
     */
    public List<RekognitionModel> getReviews() throws Exception {
        String url =
                config.getBaseUrl() + config.getBusinessId() + config.getEndpoint();

        Map<String, String> headers = new HashMap<>();
        headers.put(config.getAuthKey(), config.getAuthValue());

        HttpURLConnection response =
                apiRequestHandler.execute(
                        url,
                        config.getRequestMethod(),
                        headers,
                        config.getRequestTimeout()
                );

        apiRequestHandler.validateHttpResponse(
                response.getResponseCode(),
                HttpURLConnection.HTTP_OK
        );

        List<ReviewEntity> reviews =
                new ObjectMapper()
                        .readValue(response.getInputStream(), ReviewModel.class)
                        .getReviews();

        rekognitionClient =
                AmazonRekognitionClientBuilder
                        .standard()
                        .withRegion(config.getAwsRegion())
                        .build();

        List<RekognitionModel> reviewLabels = new ArrayList<>();

        for (Integer i = 0; i < reviews.size(); i++) {
            Map<String, String> map = getS3ObjectMap();

            String userId = reviews.get(i).getUser().getId();
            String photo = map.get(userId);

            DetectLabelsRequest rekognitionRequest =
                    makeRekognitionRequest(photo);

            List<Label> labels =
                    getRekognitionLabels(rekognitionRequest);

            RekognitionModel rekognitionModel = map(reviews.get(i), labels);

            reviewLabels.add(rekognitionModel);
        }

        return reviewLabels;
    }

    private Map<String, String> getS3ObjectMap() {
        Map<String, String> result = new HashMap<>();

        // Example output: {USER_ID: S3OBJECT.jpg}
        config.getS3Objects().forEach(n -> {
            result.put(n.split("\\.")[0], n);
        });

        return result;
    }

    private RekognitionModel map(Review review, List<Label> labels) {
        RekognitionModel reviewLabel = new RekognitionModel();

        reviewLabel.map(review);

        labels.forEach(n -> reviewLabel.getLabels().add(n.getName()));

        return reviewLabel;
    }

    private DetectLabelsRequest makeRekognitionRequest(String objectName) throws Exception {
        return new DetectLabelsRequest()
                .withMaxLabels(20)
                .withMinConfidence(75F)
                .withImage(attachS3Object(objectName));
    }

    private List<Label> getRekognitionLabels(DetectLabelsRequest detectLabelsRequest) throws Exception {
        try {
            DetectLabelsResult result = rekognitionClient.detectLabels(detectLabelsRequest);
            return result.getLabels();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private Image attachS3Object(String objectName) {
        return new Image()
                .withS3Object(new S3Object()
                        .withName(objectName)
                        .withBucket(config.getS3Bucket())
                );
    }

//    private Image toImage(String imageUrl) throws Exception {
//        InputStream inputStream = null;
//
//        ByteArrayOutputStream outputStream =
//                new ByteArrayOutputStream();
//
//        Image image = null;
//
//        try {
//            byte[] chunk = new byte[4096];
//
//            Integer bytesRead = 0;
//
//            inputStream = new URL(imageUrl).openStream();
//
//            while ((bytesRead = inputStream.read()) > 0) {
//                outputStream.write(chunk, 0, bytesRead);
//            }
//
//            // Note: Image bytes passed using the Bytes property must be base64-encoded.
//            // https://docs.aws.amazon.com/rekognition/latest/dg/API_Image.html
//            byte[] encoded = Base64.getEncoder().encode(outputStream.toByteArray());
//            image = new Image().withBytes(ByteBuffer.wrap(encoded));
//        } catch (Exception e) {
//            e.getMessage();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//        }
//        return image;
//    }

}
