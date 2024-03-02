package ru.praktikum_services.qa_scooter;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierDeleteRequest;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierLoginRequest;
import ru.praktikum_services.qa_scooter.response.courier_pojos.CourierCreateResponse;
import ru.praktikum_services.qa_scooter.response.courier_pojos.CourierLoginResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.praktikum_services.qa_scooter.api.CourierApi.*;
import static ru.praktikum_services.qa_scooter.constants.Errors.*;

public class CourierCreateApiTest extends BaseTest{
    private final CourierCreateRequest courierCreateRequest = new CourierCreateRequest(
            "TestForApi23123123123", "12342", "Testov"
    );
    private CourierCreateRequest courierCreateRequestWithoutLogin;
    private CourierCreateRequest courierCreateRequestWithoutPassword;

    @Before
    public void setUpForNegative(){
        courierCreateRequestWithoutLogin = new CourierCreateRequest();
        courierCreateRequestWithoutLogin.setPassword(courierCreateRequest.getPassword());
        courierCreateRequestWithoutLogin.setLogin(null);

        courierCreateRequestWithoutPassword = new CourierCreateRequest();
        courierCreateRequestWithoutPassword.setLogin(courierCreateRequest.getLogin());
        courierCreateRequestWithoutPassword.setPassword("");
    }

    @Test
    @DisplayName("Метод проверки статус-кода при успешном создании курьера")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (201)")
    public void checkCourierCreateStatusCode201(){
        courierCreateRequest(courierCreateRequest)
                .then()
                .assertThat()
                .statusCode(SC_CREATED);
    }
    @Test
    @DisplayName("Метод проверки сообщения при успешном создании курьера")
    @Description("Метод проверяет соответствие сообщения при создании курьера ожидаемому")
    public void checkCourierCreateHasOkMessage(){
        assertTrue("Курьер не создан",
                courierCreateRequest(courierCreateRequest)
                        .as(CourierCreateResponse.class)
                        .isOk());
    }
    @Test
    @DisplayName("Метод проверки статус-кода при попытке создания двух одинаковых курьеров")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (409)")
    public void checkCannotCreateTwoIdenticalCouriersStatusCode409(){
        courierCreateRequest(courierCreateRequest);
        courierCreateRequest(courierCreateRequest)
                .then()
                .assertThat()
                .statusCode(SC_CONFLICT);
    }
    @Test
    @DisplayName("Метод проверки сообщения при попытке создания двух одинаковых курьеров")
    @Description("Метод проверяет соответствие сообщения при создании двух одинаковых курьеров ожидаемому")
    public void checkCannotCreateTwoIdenticalCouriersMessage(){
        courierCreateRequest(courierCreateRequest);
        assertEquals("Сообщение об ошибке при создании двух одинаковых курьеров не совпадает с ожидаемым",
                TWO_IDENTICAL_COURIERS,
                courierCreateRequest(courierCreateRequest)
                        .as(CourierCreateResponse.class).getMessage());
    }

    @Test
    @DisplayName("Метод проверки статус-кода при попытке создания курьера без логина")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (400)")
    public void checkCannotCreateCourierWithoutLoginStatusCode400(){
        courierCreateRequest(courierCreateRequestWithoutLogin)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Метод проверки сообщения при попытке создания курьера без логина")
    @Description("Метод проверяет соответствие сообщения при создании курьера без логина")
    public void checkCannotCreateCourierWithoutLoginMessage(){
        assertEquals("Сообщение об ошибке при создании курьера без логина не совпадает с ожидаемым",
                WITHOUT_PASS_LOGIN,
                courierCreateRequest(courierCreateRequestWithoutLogin)
                        .as(CourierCreateResponse.class).getMessage()
        );
    }
    @Test
    @DisplayName("Метод проверки статус-кода при попытке создания курьера без пароля")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (400)")
    public void checkCannotCreateCourierWithoutPasswordStatusCode400(){
        courierCreateRequest(courierCreateRequestWithoutPassword)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Метод проверки сообщения при попытке создания курьера без пароля")
    @Description("Метод проверяет соответствие сообщения при создании курьера без пароля")
    public void checkCannotCreateCourierWithoutPasswordMessage(){
        assertEquals("Сообщение об ошибке при создании курьера без пароля не совпадает с ожидаемым",
                WITHOUT_PASS_LOGIN,
                courierCreateRequest(courierCreateRequestWithoutPassword)
                        .as(CourierCreateResponse.class).getMessage()
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
