package com.crescendo.yelpservice.handler;

import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class ApiRequestHandler implements ApiRequest {

    /**
     * Execute an API call.
     * @param resource
     * @param requestMethod
     * @param headers
     * @param requestTimeout
     * @return
     * @throws Exception
     */
    public HttpURLConnection execute(
            String resource,
            String requestMethod,
            Map<String, String> headers,
            Integer requestTimeout
    ) throws Exception {
        try {
            URL url = new URL(resource);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod(requestMethod);
            request.setConnectTimeout(requestTimeout);
            request.setReadTimeout(requestTimeout);
            headers.forEach((k,v) -> request.setRequestProperty(k ,v));
            return request;
        }
        catch (MalformedURLException e) {
            throw new MalformedURLException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Compare actual and expected values of an HTTP response.
     * @param httpResponseExpected
     * @param httpResponseActual
     * @return
     * @throws Exception
     */
    public Integer validateHttpResponse(
            Integer httpResponseExpected,
            Integer httpResponseActual
    ) throws Exception {
        if (!httpResponseExpected.equals(httpResponseActual)) {
            throw new Exception(
                    "Expected HTTP response " + httpResponseActual + " but found " + httpResponseExpected
            );
        }
        return httpResponseActual;
    }

}
