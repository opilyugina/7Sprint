package ru.praktikum;

import java.time.LocalDate;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import java.util.List;
import java.util.Locale;

public class BaseTest {
    protected CourierService courierService = new CourierService();
    protected OrderService orderService = new OrderService();

    protected Faker faker = new Faker(new Locale("ru"));
    protected Integer courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierService.deleteCourier(courierId);
        }
    }

    protected Courier generateRandomCourier() {
        return new Courier(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    protected Order generateRandomOrder(List<String> colors) {
        String tomorrowDate = LocalDate.now().plusDays(1).toString();

        return new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.number().numberBetween(1, 10),
                faker.phoneNumber().phoneNumber(),
                faker.number().numberBetween(1, 10),
                tomorrowDate,
                faker.lorem().sentence(),
                colors
        );
    }
}