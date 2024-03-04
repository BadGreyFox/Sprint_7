package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.constants.ColourOfScooter;
import ru.praktikum_services.qa_scooter.requests.orders_pojos.OrderCancelRequest;
import ru.praktikum_services.qa_scooter.requests.orders_pojos.OrderCreateRequest;
import ru.praktikum_services.qa_scooter.response.orders_pojos.OrderCreateResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;
import static ru.praktikum_services.qa_scooter.api.OrderApi.*;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class OrderCreateApiTest extends BaseTest {
    private String[] color;
    private int track;
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
    @DisplayName("Метод проверки статус-кода при успешном создании заказа авторизации с разным цветом самоката")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (201)")
    public void checkOrderCreateColorStatusCode201(){
        Response response = orderCreateRequest(request);

        track = response.as(OrderCreateResponse.class)
                .getTrack();

        response.then()
                .assertThat()
                .statusCode(SC_CREATED);
    }
    @Test
    @DisplayName("Метод проверки наличия номер трека при создании заказа")
    @Description("Метод проверяет, что в теле ответа содержится параметр с номером трека")
    public void checkOrderCreateColorHasTrack(){
        track = orderCreateRequest(request).as(OrderCreateResponse.class)
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
