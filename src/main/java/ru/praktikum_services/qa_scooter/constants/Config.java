package ru.praktikum_services.qa_scooter.constants;

public class Config {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_API = "/api/v1/courier/";
    public static final String COURIER_LOGIN = COURIER_API + "login";
    public static final String ORDER_CREATE_OR_GET = "/api/v1/orders";
    public static final String ORDER_CANCEL = ORDER_CREATE_OR_GET + "/cancel";
}
