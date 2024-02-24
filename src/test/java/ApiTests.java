import api.client.ApiClient;
import api.domain.CommentsRequests;
import api.domain.PostsRequests;
import api.helpers.RequestBodyHelper;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static api.helpers.ResponseValidationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class ApiTests {
    public static final ApiClient apiClient = new ApiClient();
    public static final PostsRequests postsRequests = new PostsRequests(apiClient);
    public static final CommentsRequests commentsRequests = new CommentsRequests(apiClient);

    @Test()
    @DisplayName("GET all posts")
    @Description("Gets all posts and checks its number is equal to expected")
    public void getAllPostsCountMatchesExpected() {
        ValidatableResponse validatableResponse = postsRequests.getAllPosts();
        int actualPostsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.POSTS_QUANTITY, actualPostsQuantity);

    }

    @Test
    @DisplayName("GET post by existing ID")
    @Description("Gets posts with ID and checks the fields")
    public void getPostByExistingId() {
        ValidatableResponse validatableResponse = postsRequests.getExactPost(TestData.POST_ID);
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.POST_WITH_ID_ONE_TITLE, actualPostTitle);
        assertEquals(TestData.POST_WITH_ID_ONE_BODY, actualPostBody);
        assertEquals(TestData.USER_ID, actualPostUserId);
        assertEquals(TestData.POST_ID, actualPostId);
    }

    @Test
    @DisplayName("GET comments for post with existing ID using query param")
    @Description("Gets comments checks that its number is as expected")
    public void getCommentsCountByPostIdQueryParam() {
        ValidatableResponse validatableResponse = commentsRequests.getExactPostComments(TestData.POST_ID);
        int actualCommentsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.COMMENTS_QUANTITY, actualCommentsQuantity);
    }

    @Test
    @DisplayName("GET comments for post with existing ID")
    @Description("Gets comments checks that its number is as expected")
    public void getCommentsCountForPostByExistingId() {
        ValidatableResponse validatableResponse = postsRequests.getCommentsToExactPost(TestData.POST_ID);
        int actualCommentsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.COMMENTS_QUANTITY, actualCommentsQuantity);
    }

    @Test
    @DisplayName("POST a new post")
    @Description("Creates a new post and checks that all fields were saved correctly")
    public void createNewPostFieldsSavedWithCorrectValues() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(TestData.POST_TITLE)
                .body(TestData.POST_BODY)
                .userId(TestData.USER_ID)
                .build();

        ValidatableResponse validatableResponse = postsRequests.createPost(requestBody.toJson());
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_CREATED);
        assertEquals(TestData.POST_TITLE, actualPostTitle);
        assertEquals(TestData.POST_BODY, actualPostBody);
        assertEquals(TestData.USER_ID, actualPostUserId);
        assertEquals(TestData.NEW_POST_ID, actualPostId);
    }

    @Test
    @DisplayName("PUT post with existing ID")
    @Description("Updates post with existing ID and checks that all fields were saved correctly")
    public void updatePostFieldsChangedCorrectly() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(TestData.UPDATED_POST_TITLE)
                .body(TestData.UPDATED_POST_BODY)
                .userId(TestData.USER_ID)
                .build();

        ValidatableResponse validatableResponse = postsRequests.updatePost(requestBody.toJson(), TestData.POST_ID);
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.UPDATED_POST_TITLE, actualPostTitle);
        assertEquals(TestData.UPDATED_POST_BODY, actualPostBody);
        assertEquals(TestData.USER_ID, actualPostUserId);
        assertEquals(TestData.POST_ID, actualPostId);
    }

    @Test
    @DisplayName("PATCH post with existing ID")
    @Description("Updates post with existing ID and checks that all fields were saved correctly")
    public void patchPostFieldsChangedCorrectly() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(TestData.POST_BODY)
                .userId(TestData.USER_ID)
                .build();

        ValidatableResponse validatableResponse = postsRequests.patchPost(requestBody.toJson(), TestData.POST_ID_TO_PATCH);

        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(TestData.POST_BODY, actualPostTitle);
        assertEquals(TestData.POST_WITH_ID_FIFTY_FIVE_BODY, actualPostBody);
        assertEquals(TestData.USER_ID, actualPostUserId);
        assertEquals(TestData.POST_ID_TO_PATCH, actualPostId);
    }

    @Test
    @DisplayName("DELETE post with existing ID")
    @Description("Delete post with existing ID and checks that deletion was successful")
    public void deletePostByExistingId() {
        ValidatableResponse validatableResponse = postsRequests.deleteExactPost(TestData.POST_ID_TO_DELETE);

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals("{}", getResponseAsString(validatableResponse));
    }

    //**************** Negative scenarios ****************
    @Test
    @DisplayName("GET post by not existing ID")
    @Description("Gets posts with ID and checks the fields")
    public void getPostByNotExistingId() {
        ValidatableResponse validatableResponse = postsRequests.getExactPost(TestData.NOT_EXISTING_POST_ID);
        //Response with java.lang.NullPointerException, but usually is implemented as NOT_FOUND
    }

    @Test
    @DisplayName("GET comments for post with not existing ID")
    @Description("Checks that GET comments for post with not existing ID returns 0")
    public void getCommentsCountForPostByNotExistingId() {
        ValidatableResponse validatableResponse = postsRequests.getCommentsToExactPost(TestData.NOT_EXISTING_POST_ID);
        int actualCommentsQuantity = getListWithValuesFromResponse(validatableResponse, "$").size();

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals(0, actualCommentsQuantity);
        // should return NOT_FOUND
    }

    @Test
    @DisplayName("DELETE post with not existing ID")
    @Description("Delete post with not existing ID and checks that returns empty JSON")
    public void deletePostByNotExistingId() {
        ValidatableResponse validatableResponse = postsRequests.deleteExactPost(TestData.NOT_EXISTING_POST_ID);

        validatableResponse.statusCode(HttpStatus.SC_OK);
        assertEquals("{}", getResponseAsString(validatableResponse));
        // should return NOT_FOUND

    }

    @Test
    @DisplayName("POST a new post with empty body")
    @Description("Creates a new post without title and body, but with userId and id")
    public void createNewPostWithEmptyBody() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder().build();

        ValidatableResponse validatableResponse = postsRequests.createPost(requestBody.toJson());
        String actualPostTitle = extractValueFromResponse(validatableResponse, "title");
        String actualPostBody = extractValueFromResponse(validatableResponse, "body");
        int actualPostUserId = extractValueFromResponse(validatableResponse, "userId");
        int actualPostId = extractValueFromResponse(validatableResponse, "id");

        validatableResponse.statusCode(HttpStatus.SC_CREATED);
        assertNull(actualPostTitle);
        assertNull(actualPostBody);
        assertEquals(0, actualPostUserId);
        assertEquals(TestData.NEW_POST_ID, actualPostId);
    }

    @Test
    @DisplayName("PUT post with not existing ID")
    @Description("Updates post with existing ID")
    public void updatePostByNotExistingId() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(TestData.UPDATED_POST_TITLE)
                .body(TestData.UPDATED_POST_BODY)
                .userId(TestData.USER_ID)
                .build();
        ValidatableResponse validatableResponse = postsRequests.updatePost(requestBody.toJson(), TestData.NOT_EXISTING_POST_ID);

        validatableResponse.statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        // should return NOT_FOUND
    }
    @Test
    @DisplayName("PATCH post with not existing ID")
    @Description("Updates post with not existing ID")
    public void patchPostByNotExistingId() {
        RequestBodyHelper requestBody = RequestBodyHelper.builder()
                .title(TestData.POST_BODY)
                .userId(TestData.USER_ID)
                .build();
        ValidatableResponse validatableResponse = postsRequests.patchPost(requestBody.toJson(), TestData.NOT_EXISTING_POST_ID);

        validatableResponse.statusCode(HttpStatus.SC_OK);
       // should return NOT_FOUND
    }
}