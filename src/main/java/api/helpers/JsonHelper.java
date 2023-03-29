package api.helpers;


import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonHelper {

    public String createNewPostBody(String title, String body, int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("body", body);
        map.put("userId", userId);
        return new JSONObject(map).toJSONString();
    }
}
