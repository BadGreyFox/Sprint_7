package ru.praktikum.services.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.services.scooter.requests.courier.CourierCreateRequest;
import ru.praktikum.services.scooter.requests.courier.CourierDeleteRequest;
import ru.praktikum.services.scooter.requests.courier.CourierLoginRequest;

import static ru.praktikum.services.scooter.api.BaseAPI.deleteRequest;
import static ru.praktikum.services.scooter.api.BaseAPI.postRequest;
import static ru.praktikum.services.scooter.constants.Config.COURIER_API;
import static ru.praktikum.services.scooter.constants.Config.COURIER_LOGIN;

public class CourierApi {
    @Step("Отправка POST-запроса на ручку создания курьера")
    public static Response courierCreateRequest(CourierCreateRequest json) {
        return postRequest(json, COURIER_API);
    }
    @Step("Отправка POST-запроса на ручку авторизации курьера")
    public static Response courierLoginRequest(CourierLoginRequest json) {
        return postRequest(json, COURIER_LOGIN);
    }
    @Step("Отправка DELETE-запроса на ручку удаления курьера")
    public static void courierDeleteRequest(CourierDeleteRequest request){
        deleteRequest(COURIER_API + request.getId());
    }
}
