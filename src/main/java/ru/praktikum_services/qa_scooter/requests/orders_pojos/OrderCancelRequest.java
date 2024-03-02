package ru.praktikum_services.qa_scooter.requests.orders_pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelRequest {
    private int track;
}
