package ru.praktikum.services.scooter.response.courier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierCreateResponse {
    private boolean ok;
    private String message;
}
