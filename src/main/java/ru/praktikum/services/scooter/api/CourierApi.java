package ru.praktikum.services.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierCreateRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierDeleteRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierLoginRequest;

import static io.restassured.RestAssured.given;
import static ru.praktikum.services.scooter.constants.Config.COURIER_API;
import static ru.praktikum.services.scooter.constants.Config.COURIER_LOGIN;

public class CourierApi {
    @Step("Отправка POST-запроса на ручку создания курьера")
    public static Response courierCreateRequest(CourierCreateRequest json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_API);
    }
    @Step("Отправка POST-запроса на ручку авторизации курьера")
    public static Response courierLoginRequest(CourierLoginRequest json) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(COURIER_LOGIN);
    }
    @Step("Отправка DELETE-запроса на ручку удаления курьера")
    public static void courierDeleteRequest(CourierDeleteRequest request){
            given()
                .when()
                .delete(COURIER_API + request.getId());
    }
}
