package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверяет, что если курьер вводит валидные логин и пароль во время входа в систему, то возвращается 200 ОК")
    public void courierCanLogin() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        courierId = courierService.loginCourier(courier)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным паролем")
    @Description("Проверяет, что если курьер вводит неверный пароль во время входа в систему, то возвращается 404 Not Found и " +
            "сообщение о том, что учетная запись не найдена.")
    public void cannotLoginWithWrongPassword() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        courierService.loginCourier(new Courier(courier.getLogin(), "wrong_password", null))
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    @Description("Проверяет, что если курьер пытается войти в систему без ввода пароля, то возвращается 400 Bad Request " +
            "и сообщение о нехватке данных для входа.")
    public void cannotLoginWithoutPassword() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        courierService.loginCourier(new Courier(courier.getLogin(), null, null))
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}