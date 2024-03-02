package ru.praktikum_services.qa_scooter.response.courier_pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierCreateResponse {
    private boolean ok;
    private String message;
}
