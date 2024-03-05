package ru.praktikum.services.scooter.test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.services.scooter.api.CourierApi;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierCreateRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierDeleteRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierLoginRequest;
import ru.praktikum.services.scooter.response.courier_pojos.CourierLoginResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.praktikum.services.scooter.api.CourierApi.courierLoginRequest;
import static ru.praktikum.services.scooter.constants.Errors.*;

public class CourierLoginApiTest extends BaseTest{
    private CourierLoginRequest loginRequest;
    @Before
    public void setUpWithCreateCourier(){
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(
                RandomStringUtils.randomAlphabetic(9),
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(9)
        );

        loginRequest = new CourierLoginRequest(
                courierCreateRequest.getLogin(),
                courierCreateRequest.getPassword());
        CourierApi.courierCreateRequest(courierCreateRequest);
    }

    @Test
    @DisplayName("Метод проверки авторизации курьера")
    @Description("Метод проверяет, что id из ответа содержит данные и соответствие статус-кода ожидаемому (200)")
    public void checkCourierSuccessLogin(){
        Response response = courierLoginRequest(loginRequest);

        response.then()
                .assertThat()
                .statusCode(SC_OK);

        assertNotEquals( 0,
                (response.as(CourierLoginResponse.class)
                .getId()));
    }
    @Test
    @DisplayName("Метод проверки попытки авторизации курьера без логина")
    @Description("Метод проверяет соответствие сообщения об ошибке и статус-кода ожидаемым (400)")
    public void checkCourierLoginWithoutLogin(){
        loginRequest.setLogin("");
        Response response = courierLoginRequest(loginRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                LOGIN_WITHOUT_PASS_LOGIN,
                response.as(CourierLoginResponse.class)
                        .getMessage()
        );
    }
    @Test
    @DisplayName("Метод проверки попытки авторизации курьера без пароля")
    @Description("Метод проверяет соответствие сообщения об ошибке и статус-кода ожидаемым (400)")
    public void checkCourierLoginWithoutPassword(){
        loginRequest.setPassword("");
        Response response = courierLoginRequest(loginRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                LOGIN_WITHOUT_PASS_LOGIN,
                response.as(CourierLoginResponse.class)
                        .getMessage());
    }
    @Test
    @DisplayName("Метод проверки попытки авторизации несуществующего курьера")
    @Description("Метод проверяет соответствие сообщения об ошибке и статус-кода ожидаемому (404)")
    public void checkCourierLoginWithNonExistentLogin(){
        loginRequest.setLogin(loginRequest.getLogin() + "1");
        Response response = courierLoginRequest(loginRequest);

        response.then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);

        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                NON_EXISTENT_LOGIN,
                response.as(CourierLoginResponse.class)
                        .getMessage());
    }

    @After
    public void cleanUp(){
        CourierLoginResponse response = courierLoginRequest(loginRequest).
                as(CourierLoginResponse.class);
        CourierApi.courierDeleteRequest(new CourierDeleteRequest(response.getId()));
    }
}
