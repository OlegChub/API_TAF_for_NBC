package api.domain;

import api.client.ApiClient;
import api.client.requests.RequestMethod;
import io.restassured.response.ValidatableResponse;

public class CommentsRequests {
    private final String COMMENTS_ENDPOINT = "/comments";
    private final ApiClient api;

    public CommentsRequests(ApiClient api) {
        this.api = api;
    }

    public ValidatableResponse getExactPostComments(int postId) {
        api.addQueryParams("postId", String.valueOf(postId));
        return api.buildRequest(RequestMethod.GET, COMMENTS_ENDPOINT);
    }
}
