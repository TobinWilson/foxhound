package com.crescendo.yelpservice.handler;

import java.net.HttpURLConnection;
import java.util.Map;

public interface ApiRequest {

    HttpURLConnection execute(String resource, String requestMethod, Map<String, String> headers, Integer requestTimeout) throws Exception;

    Integer validateHttpResponse(Integer httpResponseExpected, Integer httpResponseActual) throws Exception;

}
