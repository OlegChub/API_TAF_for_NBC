package api.helpers;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestBodyHelper {
    private String title;
    private String body;
    private int userId;

    public String toJson() {
        return new Gson().toJson(this);
    }
}
