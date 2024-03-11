package ru.praktikum.services.scooter.response.orders;

import lombok.Data;

@Data
public class PageInfo {
    private int page;
    private int total;
    private int limit;
}
