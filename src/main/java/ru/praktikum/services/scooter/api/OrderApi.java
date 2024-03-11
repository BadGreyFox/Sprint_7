package ru.praktikum.services.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.scooter.requests.orders.OrderCancelRequest;
import ru.praktikum.services.scooter.requests.orders.OrderCreateRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.praktikum.services.scooter.api.BaseAPI.*;
import static ru.praktikum.services.scooter.constants.Config.*;

public class OrderApi {
    @Step("Отправка POST-запроса на ручку создания заказа")
    public static Response orderCreateRequest (OrderCreateRequest json) {
       return postRequest(json,ORDER_CREATE_OR_GET);
    }
    @Step("Отправка GET-запроса на ручку получения списка заказов")
    public static Response orderGetRequest (Map<String, Object> map) {
       return getRequest(map,ORDER_CREATE_OR_GET);
    }
    @Step("Отправка PUT-запроса на ручку отмены заказа")
    public static void orderCancelRequest (OrderCancelRequest json) {
        given()
                .header("Content-type", "application/json")
                .and()
                .queryParam("track", json.getTrack())
                .when()
                .put(ORDER_CANCEL);
    }
}
