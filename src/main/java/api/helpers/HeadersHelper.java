package api.helpers;

import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

public class HeadersHelper {
    static Map<String, String> headers = new HashMap<>();

    public static Map<String, String> getDefaultHeaders(){
        headers.put("Content-Type", String.valueOf(ContentType.JSON));
        headers.put("Accept", "*/*");
        return headers;
    }
}
