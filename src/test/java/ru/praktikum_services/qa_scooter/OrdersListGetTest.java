package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.response.orders_pojos.OrdersGetResponse;

import java.util.List;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertNotNull;
import static ru.praktikum_services.qa_scooter.api.OrderApi.orderGetRequest;
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
    @DisplayName("Метод проверки наличия списка заказов")
    @Description("Метод проверяет, что в теле ответа содержится лист с заказами")
    public void checkGetOrdersListHasOrders(){
        assertNotNull("Заказы не найдены", orderGetRequest(map)
                .as(OrdersGetResponse.class)
                .getOrders());
    }
    @Test
    @DisplayName("Метод проверки статус-кода при запросе списка заказов")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkGetOrdersListStatusCode(){
        orderGetRequest(map)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }
}
