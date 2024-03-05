package ru.praktikum.services.scooter.test;

import io.restassured.RestAssured;
import org.junit.Before;
import ru.praktikum.services.scooter.constants.Config;


public abstract class BaseTest {
    @Before
    public void setUp() {
        RestAssured.baseURI= Config.BASE_URI;
    }

}
