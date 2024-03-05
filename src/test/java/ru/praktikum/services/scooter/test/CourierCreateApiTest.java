package ru.praktikum.services.scooter.test;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierCreateRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierDeleteRequest;
import ru.praktikum.services.scooter.requests.courier_pojos.CourierLoginRequest;
import ru.praktikum.services.scooter.response.courier_pojos.CourierCreateResponse;
import ru.praktikum.services.scooter.response.courier_pojos.CourierLoginResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.praktikum.services.scooter.api.CourierApi.*;
import static ru.praktikum.services.scooter.constants.Errors.*;

public class CourierCreateApiTest extends BaseTest{
    private CourierCreateRequest courierCreateRequest;
    @Before
    public void setUpForNegative(){
        courierCreateRequest = new CourierCreateRequest(
                RandomStringUtils.randomAlphabetic(9),
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }

    @Test
    @DisplayName("Метод проверки создания курьера")
    @Description("Метод проверяет соответствие сообщения при создании курьера ожидаемому и статус-кода ожидаемым (201)")
    public void checkCourierCreate(){
        Response response = courierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);

        assertTrue("Курьер не создан",
                response.as(CourierCreateResponse.class)
                        .isOk());
    }
    @Test
    @DisplayName("Метод проверки попытки создания двух одинаковых курьеров")
    @Description("Метод проверяет соответствие сообщения при создании двух одинаковых курьеров и статус-кода ожидаемому (409)")
    public void checkCannotCreateTwoIdenticalCouriers(){
       courierCreateRequest(courierCreateRequest);

       Response response = courierCreateRequest(courierCreateRequest);

       response.then()
                .assertThat()
                .statusCode(SC_CONFLICT);

       assertEquals("Сообщение об ошибке при создании двух одинаковых курьеров не совпадает с ожидаемым",
                TWO_IDENTICAL_COURIERS,
                response.as(CourierCreateResponse.class).getMessage());
    }

    @Test
    @DisplayName("Метод проверки попытки создания курьера без логина")
    @Description("Метод проверяет соответствие сообщения при создании курьера без логина и статус-кода ожидаемому (400)")
    public void checkCannotCreateCourierWithoutLogin(){
        courierCreateRequest.setLogin(null);

        Response response = courierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals("Сообщение об ошибке при создании курьера без логина не совпадает с ожидаемым",
                WITHOUT_PASS_LOGIN,
                response
                        .as(CourierCreateResponse.class).getMessage()
        );
    }
    @Test
    @DisplayName("Метод проверки попытки создания курьера без пароля")
    @Description("Метод проверяет соответствие сообщения при создании курьера без пароля и статус-кода ожидаемому (400)")
    public void checkCannotCreateCourierWithoutPassword(){
        courierCreateRequest.setPassword("");

        Response response = courierCreateRequest(courierCreateRequest);

        response.then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);

        assertEquals("Сообщение об ошибке при создании курьера без пароля не совпадает с ожидаемым",
                WITHOUT_PASS_LOGIN,
                response.as(CourierCreateResponse.class).getMessage()
        );
    }
    @After
    public void cleanUp(){
       CourierLoginResponse response = courierLoginRequest(
               new CourierLoginRequest(
                       courierCreateRequest.getLogin(),
                       courierCreateRequest.getPassword())
       ).as(CourierLoginResponse.class);
        courierDeleteRequest(new CourierDeleteRequest(response.getId()));
    }
}
