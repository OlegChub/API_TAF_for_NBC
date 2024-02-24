package api.domain;

import api.client.ApiClient;
import api.client.requests.RequestMethod;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class PostsRequests {
    //TODO create endpoints class
    private final String POSTS_ENDPOINT = "/posts";
    private final ApiClient api;

    public PostsRequests(ApiClient api) {
        this.api = api;
    }

    public ValidatableResponse getAllPosts() {
        return api.buildRequest(RequestMethod.GET, POSTS_ENDPOINT);
    }

    public ValidatableResponse getExactPost(int postId) {
        return api.buildRequest(RequestMethod.GET, String.format("%s/%d", POSTS_ENDPOINT, postId));
    }

    public ValidatableResponse getCommentsToExactPost(int postId) {
        return api.buildRequest(RequestMethod.GET, String.format("%s/%d/comments", POSTS_ENDPOINT, postId));
    }

    public ValidatableResponse createPost(Object object) {
        api.setRequestBody(object);
        api.addHeader("Content-type", ContentType.JSON.withCharset("UTF-8"));
        return api.buildRequest(RequestMethod.POST, POSTS_ENDPOINT);
    }

    public ValidatableResponse updatePost(Object object, int postId) {
        api.setRequestBody(object);
        api.addHeader("Content-type", ContentType.JSON.withCharset("UTF-8"));
        return api.buildRequest(RequestMethod.PUT, String.format("%s/%d", POSTS_ENDPOINT, postId));
    }
    public ValidatableResponse patchPost(Object object, int postId) {
        api.setRequestBody(object);
        api.addHeader("Content-type", ContentType.JSON.withCharset("UTF-8"));
        return api.buildRequest(RequestMethod.PATCH, String.format("%s/%d", POSTS_ENDPOINT, postId));
    }

    public ValidatableResponse deleteExactPost(int postId) {
        return api.buildRequest(RequestMethod.DELETE, String.format("%s/%d", POSTS_ENDPOINT, postId));
    }


}
