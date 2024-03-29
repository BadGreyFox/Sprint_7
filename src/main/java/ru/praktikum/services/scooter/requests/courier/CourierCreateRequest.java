package ru.praktikum.services.scooter.requests.courier;

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
