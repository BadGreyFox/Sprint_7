package ru.praktikum.services.scooter.test;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.services.scooter.response.orders.OrdersGetResponse;

import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertNotNull;
import static ru.praktikum.services.scooter.api.OrderApi.orderGetRequest;
@RunWith(Parameterized.class)
@AllArgsConstructor
public class OrdersListGetTest extends BaseTest {
    Map<String, Object> map;

    @Parameterized.Parameters()
    public static List<Map<Object, Object>> getDataForOrder() {
        return List.of(
                Map.of(
                "courierId","12131"),
                Map.of(
                "nearestStation","4"),
                Map.of(
                "limit","15"),
                Map.of(
                "page","1"),
                Map.of()
        );
    }
    @Test
    @DisplayName("Метод проверки получения списка заказов")
    @Description("Метод проверяет, что в теле ответа содержится лист с заказами и соответствие статус-кода ожидаемому (200)")
    public void checkGetOrdersListStatusCode(){
        Response response = orderGetRequest(map);

        response.then()
                .assertThat()
                .statusCode(SC_OK);

        assertNotNull("Заказы не найдены",
                response.as(OrdersGetResponse.class)
                .getOrders());
    }
}
