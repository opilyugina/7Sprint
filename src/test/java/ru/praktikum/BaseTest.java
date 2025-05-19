package ru.praktikum;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import java.util.List;

public class BaseTest {

    protected Integer courierId = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    public void cleanup() {
        if (courierId != null && courierId > 0) {
            Courier.delete(courierId);
        }
    }

    protected Courier createCourierWithUniqueLogin(String password, String firstName) {
        String uniqueLogin = "login_" + System.currentTimeMillis();
        Courier courier = new Courier(uniqueLogin, password, firstName);

        courier.create()
                .then()
                .statusCode(201);

        courierId = Courier.login(uniqueLogin, password)
                .then()
                .extract()
                .path("id");
        return courier;
    }

    protected Order createOrder(List<String> color) {
        return new Order(
                "Test", "User", "Test Street, 1",
                4, "+79990000000", 5,
                "2025-05-15", "Test comment", color
        );
    }
}