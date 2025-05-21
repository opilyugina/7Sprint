package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверяет, что при вводе валидных логина и пароля возвращается 200 ОК и id")
    public void courierCanLogin() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        CourierModel model = new CourierModel(courier.getLogin(), courier.getPassword());

        courierId = courierService.loginCourier(model)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным паролем")
    @Description("Проверяет, что возвращается 404 при неверном пароле")
    public void cannotLoginWithWrongPassword() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        CourierModel model = new CourierModel(courier.getLogin(), "wrong_password");

        courierService.loginCourier(model)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    @Description("Проверяет, что возвращается 400 при отсутствии пароля")
    public void cannotLoginWithoutPassword() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        CourierModel model = new CourierModel(courier.getLogin(), null);

        courierService.loginCourier(model)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без логина")
    @Description("Проверяет, что возвращается 400 при отсутствии логина")
    public void cannotLoginWithoutLogin() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        CourierModel model = new CourierModel(null, courier.getPassword());

        courierService.loginCourier(model)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным логином")
    @Description("Проверяет, что возвращается 404 при неверном логине")
    public void cannotLoginWithWrongLogin() {
        Courier courier = generateRandomCourier();
        courierService.createCourier(courier);

        CourierModel model = new CourierModel("wrong_login", courier.getPassword());

        courierService.loginCourier(model)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}