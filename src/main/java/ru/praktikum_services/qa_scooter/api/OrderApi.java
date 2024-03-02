package ru.praktikum_services.qa_scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.requests.orders_pojos.OrderCancelRequest;
import ru.praktikum_services.qa_scooter.requests.orders_pojos.OrderCreateRequest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.praktikum_services.qa_scooter.constants.Config.ORDER_CANCEL;
import static ru.praktikum_services.qa_scooter.constants.Config.ORDER_CREATE_OR_GET;

public class OrderApi {
    @Step("Отправка POST-запроса на ручку создания заказа")
    public static Response orderCreateRequest (OrderCreateRequest json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(ORDER_CREATE_OR_GET);
    }
    @Step("Отправка GET-запроса на ручку получения списка заказов")
    public static Response orderGetRequest (Map<String, Object> map) {
        return given()
                .queryParams(map)
                .when()
                .get(ORDER_CREATE_OR_GET);
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
