package api.client;

import api.client.requests.RequestMethod;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static api.helpers.HeadersHelper.getDefaultHeaders;
import static api.config.ProjectSetUp.getHost;

public class ApiClient {

    private final String baseUrl = getHost();
    private final Map<String, String> headers = new HashMap<>();
    private Object requestBody;

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public ValidatableResponse buildRequest(RequestMethod method, String endpoint) {
        RequestSpecification request = RestAssured.given()
                .log().all()
                .baseUri(baseUrl);

        if (headers.size() == 0) {
            request.headers(getDefaultHeaders());
        } else {
            request.headers(headers);
        }

        if (requestBody != null) {
            request.body(requestBody);
        }

        return request.when()
                .request(String.valueOf(method), endpoint)
                .then().log().all();

    }
}
