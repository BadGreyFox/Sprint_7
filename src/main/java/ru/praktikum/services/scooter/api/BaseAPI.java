package ru.praktikum.services.scooter.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseAPI {
    public static Response postRequest(Object json, String api){
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(json)
                .when()
                .post(api);
    }
    public static Response getRequest(Map<String, Object> map, String api){
        return given()
                .queryParams(map)
                .when()
                .get(api);
    }
}
