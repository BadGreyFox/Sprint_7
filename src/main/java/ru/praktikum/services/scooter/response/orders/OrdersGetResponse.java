package ru.praktikum.services.scooter.response.orders;

import lombok.Data;

import java.util.List;
@Data
public class OrdersGetResponse {
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;
}
