package ru.praktikum.services.scooter.test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.services.scooter.requests.orders_pojos.OrderCancelRequest;
import ru.praktikum.services.scooter.requests.orders_pojos.OrderCreateRequest;
import ru.praktikum.services.scooter.constants.ColourOfScooter;
import ru.praktikum.services.scooter.response.orders_pojos.OrderCreateResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;
import static ru.praktikum.services.scooter.api.OrderApi.*;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class OrderCreateApiTest extends BaseTest {
    private String[] color;
    private static int track;
    private final OrderCreateRequest request = new OrderCreateRequest(
            "Тест",
            "Тестов",
            "Улица Пушкина, дом Колотушкино 2",
            "4",
            "89898989898",
            5,
            "2020-06-06",
            "Вы предпочитаете клонов с Сусаноо или без?"
    );
    @Parameterized.Parameters()
    public static Object[][] getDataForOrder() {
        return new Object[][]{
                {new String[]{
                        ColourOfScooter.BLACK.toString(),
                        ColourOfScooter.GREY.toString()
                }},
                {new String[]{
                        ColourOfScooter.BLACK.toString()
                }},
                {new String[]{
                        ColourOfScooter.GREY.toString()
                }},
                {new String[]{}}
        };
    }
    @Before
    public void setUpColor(){
        request.setColor(this.color);
    }
    @Test
    @DisplayName("Метод проверки создании заказа авторизации с разным цветом самоката")
    @Description("Метод проверяет, что в теле ответа содержится параметр с номером трека и соответствие статус-кода ожидаемому (201)")
    public void checkOrderCreateWithColor(){
        Response response = orderCreateRequest(request);

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);

        track = response.as(OrderCreateResponse.class)
                .getTrack();

        assertNotEquals("Отсутствует номер трека", 0, track);
    }
    @After
    public void cleanUp() {
        if (track != 0) {
            orderCancelRequest(new OrderCancelRequest(track));
        }
    }
}
