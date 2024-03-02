package ru.praktikum_services.qa_scooter;

import io.restassured.RestAssured;
import org.junit.Before;
import ru.praktikum_services.qa_scooter.constants.Config;



public abstract class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI= Config.BASE_URI;
    }

}
