package api.helpers;

import io.restassured.response.ValidatableResponse;

import java.util.List;

public class ResponseValidationHelper {
    public static <T> T extractValueFromResponse(ValidatableResponse validatableResponse, String path){
       return validatableResponse.extract().body().jsonPath().getJsonObject(path);
    }

    public static String getResponseAsString(ValidatableResponse validatableResponse){
        return validatableResponse.extract().body().asString();
    }
    public static List<Object> getListWithValuesFromResponse(ValidatableResponse validatableResponse, String path){
        return validatableResponse.extract().body().jsonPath().getList(path);
    }
}
