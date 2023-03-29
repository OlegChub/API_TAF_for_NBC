package api.domain;

import api.client.ApiClient;
import api.client.requests.RequestMethod;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class PostRequests {
    private final String ENDPOINT = "/posts";
    private final ApiClient api;

    public PostRequests(ApiClient api) {
        this.api = api;
    }

    public ValidatableResponse getAllPosts() {
        return api.buildRequest(RequestMethod.GET, ENDPOINT);
    }

    public ValidatableResponse getExactPost(int id) {
        return api.buildRequest(RequestMethod.GET, ENDPOINT + "/" + id);
    }

    public ValidatableResponse createPost(Object object) {
        api.setRequestBody(object);
        api.addHeader("Content-type", ContentType.JSON.withCharset("UTF-8"));
        return api.buildRequest(RequestMethod.POST, ENDPOINT);
    }
}
