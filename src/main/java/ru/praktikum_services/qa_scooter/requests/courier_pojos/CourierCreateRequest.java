package ru.praktikum_services.qa_scooter.requests.courier_pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierCreateRequest {
    private String login;
    private String password;
    private String firstName;
}
