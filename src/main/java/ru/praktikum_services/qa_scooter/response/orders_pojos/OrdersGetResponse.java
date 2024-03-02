package ru.praktikum_services.qa_scooter.response.orders_pojos;

import lombok.Data;

import java.util.List;
@Data
public class OrdersGetResponse {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;
}
