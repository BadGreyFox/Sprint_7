package ru.praktikum_services.qa_scooter.response.orders_pojos;

import lombok.Data;

@Data
public class PageInfo {
    private int page;
    private int total;
    private int limit;
}
