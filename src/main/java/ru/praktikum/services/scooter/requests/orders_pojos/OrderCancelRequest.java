package ru.praktikum.services.scooter.requests.orders_pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelRequest {
    private int track;
}
