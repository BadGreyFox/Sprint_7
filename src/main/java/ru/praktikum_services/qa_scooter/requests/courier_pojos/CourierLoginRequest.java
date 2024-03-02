package ru.praktikum_services.qa_scooter.requests.courier_pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierLoginRequest {
    private String login;
    private String password;
    public CourierLoginRequest(CourierLoginRequest courierLoginRequest){
        this.login = courierLoginRequest.getLogin();
        this.password = courierLoginRequest.getLogin();
    }
}
