import api.client.ApiClient;
import api.domain.CommentsRequests;
import api.domain.PostRequests;
import api.helpers.RequestBodyHelper;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.*;

import static api.helpers.ResponseValidationHelper.*;
import static org.testng.Assert.assertEquals;


public class ApiTests {

    private final ApiClient apiClient = new ApiClient();
    private final PostRequests postRequests = new PostRequests(apiClient);
    private final CommentsRequests commentsRequests = new CommentsRequests(apiClient);
    private final int POSTS_QUANTITY = 100;
    private final int COMMENTS_QUANTITY = 5;
    private final int POST_ID = 1;
    private final int NEW_POST_ID = 101;
    private final String POST_TITLE = "My story";
    private final String POST_WITH_ID_ONE_TITLE = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
    private final String POST_WITH_ID_ONE_BODY = "quia et suscipit\nsuscipit recusandae consequuntur expedita et " +
            "cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto";
    private final String POST_WITH_ID_FIFTY_FIVE_BODY="debitis excepturi ea perferendis harum libero optio\neos accusamus" +
            " cum fuga ut sapiente repudiandae\net ut incidunt omnis molestiae\nnihil ut eum odit";

    private final String UPDATED_POST_TITLE = "My NEW story";
    private final String POST_BODY = "My fabulous and fantastic story";
    private final String UPDATED_POST_BODY = "My NEW fabulous and fantastic story";
    private final int POST_ID_TO_PATCH = 55;
    private final int POST_ID_TO_DELETE = 10;
    private final int USER_ID = 1;

    @Test()
    public void getAllPostsCountMatchesExpected() {
        ValidatableResponse validatableResponse = postRequests.getAllPosts();
        int actualPostsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(POSTS_QUANTITY, actualPostsQuantity);

    }

    @Test
    public void getPostByExistingId() {
        ValidatableResponse validatableResponse = postRequests.getExactPost(POST_ID);
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(POST_WITH_ID_ONE_TITLE, actualPostTitle);
        assertEquals(POST_WITH_ID_ONE_BODY, actualPostBody);
        assertEquals(USER_ID, actualPostUserId);
        assertEquals(POST_ID, actualPostId);
    }

    @Test
    public void getCommentsCountByPostIdQueryParam() {
        ValidatableResponse validatableResponse = commentsRequests.getExactPostComments(POST_ID);
        int actualCommentsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(COMMENTS_QUANTITY, actualCommentsQuantity);
    }

    @Test
    public void getCommentsCountForPostByExistingId() {
        ValidatableResponse validatableResponse = postRequests.getCommentsToExactPost(POST_ID);
        int actualCommentsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(COMMENTS_QUANTITY, actualCommentsQuantity);
    }

    @Test
    public void createNewPostFieldsSavedWithCorrectValues() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                //TODO
                .title(POST_TITLE)
                .body(POST_BODY)
                //TODO
                .userId(USER_ID)
                .build();


        ValidatableResponse validatableResponse = postRequests.createPost(requestBody.toJson());
        //TODO:
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_CREATED);
        assertEquals(POST_TITLE, actualPostTitle);
        assertEquals(POST_BODY, actualPostBody);
        assertEquals(USER_ID, actualPostUserId);
        assertEquals(NEW_POST_ID, actualPostId);
    }

    @Test
    public void updatePostFieldsChangedCorrectly() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(UPDATED_POST_TITLE)
                .body(UPDATED_POST_BODY)
                .userId(USER_ID)
                .build();

        ValidatableResponse validatableResponse = postRequests.updatePost(requestBody.toJson(), POST_ID);
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(UPDATED_POST_TITLE, actualPostTitle);
        assertEquals(UPDATED_POST_BODY, actualPostBody);
        assertEquals(USER_ID, actualPostUserId);
        assertEquals(POST_ID, actualPostId);
    }

    @Test
    public void patchPostFieldsChangedCorrectly() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(POST_BODY)
                .userId(USER_ID)
                .build();

        ValidatableResponse validatableResponse = postRequests.patchPost(requestBody.toJson(), POST_ID_TO_PATCH);

        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        //TODO:
        assertEquals(POST_BODY, actualPostTitle);
        assertEquals(POST_WITH_ID_FIFTY_FIVE_BODY, actualPostBody);
        assertEquals(USER_ID, actualPostUserId);
        assertEquals(POST_ID_TO_PATCH, actualPostId);
    }

    @Test
    public void deletePostByExistingId() {
        ValidatableResponse validatableResponse = postRequests.deleteExactPost(POST_ID_TO_DELETE);

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals("{}", getResponseAsString(validatableResponse));
    }


}