import api.client.ApiClient;
import api.domain.PostRequests;
import api.helpers.JsonHelper;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;


public class ApiTests {

    private final int POST_ID = 1;
    private final ApiClient apiClient = new ApiClient();
    private final PostRequests postRequests = new PostRequests(apiClient);
    private final JsonHelper jsonHelper = new JsonHelper();

    @Test
    public void getPosts() {
        postRequests.getAllPosts().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getExactPost() {
        postRequests.getExactPost(POST_ID).statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createNewPost() {
        String requestBody = jsonHelper.createNewPostBody("My story",
                "My fabulous and fantastic story", 1);
        postRequests.createPost(requestBody).statusCode(HttpStatus.SC_CREATED);
    }

}