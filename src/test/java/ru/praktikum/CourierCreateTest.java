package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierCreateTest extends BaseTest {

    @Test
    @DisplayName("Можно создать курьера с валидными данными")
    @Description("Проверяет, что при отправке корректных логина, пароля и имени, курьер успешно создается (201 Created, ok=true).")
    public void createCourierWithValidData() {
        Courier courier = generateRandomCourier();

        courierService.createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверяет, что при попытке создать дубликат курьера возвращается 409 Conflict и сообщение об ошибке)")
    public void cannotCreateDuplicateCourier() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        courierService.createCourier(courier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверяет, что при попытке создать курьера без логина возвращается 400 Bad Request и сообщение об ошибке.")
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, faker.internet().password(), faker.name().firstName());

        courierService.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    @Description("Проверяет, что при попытке создать курьера без пароля возвращается 400 Bad Request и сообщение об ошибке.")
    public void cannotCreateCourierWithoutPassword() {
        Courier courier = new Courier(faker.name().username(), null, faker.name().firstName());

        courierService.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}