package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierCreateRequest;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierDeleteRequest;
import ru.praktikum_services.qa_scooter.requests.courier_pojos.CourierLoginRequest;
import ru.praktikum_services.qa_scooter.response.courier_pojos.CourierLoginResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.praktikum_services.qa_scooter.api.CourierApi.*;
import static ru.praktikum_services.qa_scooter.constants.Errors.*;

public class CourierLoginApiTest extends BaseTest{
    private CourierLoginRequest loginRequest;
    private CourierLoginRequest loginRequestWithoutLogin;
    private CourierLoginRequest loginRequestWithoutPassword;
    private CourierLoginRequest loginRequestWithNonExistentLogin;
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
        courierCreateRequest(courierCreateRequest);

        loginRequestWithoutLogin = new CourierLoginRequest(loginRequest);
        loginRequestWithoutPassword = new CourierLoginRequest(loginRequest);
        loginRequestWithNonExistentLogin = new CourierLoginRequest(loginRequest);


        loginRequestWithoutLogin.setLogin("");
        loginRequestWithoutPassword.setPassword("");
        loginRequestWithNonExistentLogin.setLogin(loginRequest.getLogin() + "1");
    }

    @Test
    @DisplayName("Метод проверки статус-кода при успешной авторизации курьера")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkCourierSuccessLoginStatusCode200(){
        courierLoginRequest(loginRequest)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Метод проверки наличия id при успешной авторизации курьера")
    @Description("Метод проверяет, что id из ответа содержит данные")
    public void checkCourierSuccessLoginReturnId(){
        assertNotNull(Integer.toString(courierLoginRequest(loginRequest)
                .as(CourierLoginResponse.class)
                .getId()));
    }
    @Test
    @DisplayName("Метод проверки статус-кода при попытке авторизации курьера без логина")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (400)")
    public void checkCourierLoginWithoutLoginStatusCode400(){
        courierLoginRequest(loginRequestWithoutLogin)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Метод проверки сообщения об ошибке при попытке авторизации курьера без логина")
    @Description("Метод проверяет соответствие сообщения об ошибке ожидаемому")
    public void checkCourierLoginWithoutLoginMessage(){
        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                LOGIN_WITHOUT_PASS_LOGIN,
                courierLoginRequest(loginRequestWithoutLogin)
                        .as(CourierLoginResponse.class)
                        .getMessage()
                );
    }
    @Test
    @DisplayName("Метод проверки статус-кода при попытке авторизации курьера без пароля")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (400)")
    public void checkCourierLoginWithoutPasswordStatusCode400(){
        courierLoginRequest(loginRequestWithoutPassword)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Метод проверки сообщения об ошибке при попытке авторизации курьера без пароля")
    @Description("Метод проверяет соответствие сообщения об ошибке ожидаемому")
    public void checkCourierLoginWithoutPasswordMessage(){
        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                LOGIN_WITHOUT_PASS_LOGIN,
                courierLoginRequest(loginRequestWithoutPassword)
                        .as(CourierLoginResponse.class)
                        .getMessage()
        );
    }
    @Test
    @DisplayName("Метод проверки статус-кода при попытке авторизации несуществующего курьера")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (404)")
    public void checkCourierLoginWithNonExistentLoginStatusCode404(){
        courierLoginRequest(loginRequestWithNonExistentLogin)
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }
    @Test
    @DisplayName("Метод проверки сообщения об ошибке при попытке авторизации несуществующего курьера")
    @Description("Метод проверяет соответствие сообщения об ошибке ожидаемому")
    public void checkCourierLoginWithNonExistentLoginMessage(){
        assertEquals("Сообщение об ошибке авторизации не совпадает с ожидаемым",
                NON_EXISTENT_LOGIN,
                courierLoginRequest(loginRequestWithNonExistentLogin)
                        .as(CourierLoginResponse.class)
                        .getMessage()
        );
    }

    @After
    public void cleanUp(){
        CourierLoginResponse response = courierLoginRequest(loginRequest).
                as(CourierLoginResponse.class);
        courierDeleteRequest(new CourierDeleteRequest(response.getId()));
    }
}
