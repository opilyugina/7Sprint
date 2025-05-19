package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CourierTest extends BaseTest {

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCreateCourierSuccess() {
        Courier courier = createCourierWithUniqueLogin("pass1", "Ivan");
        assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Ошибка при создании дубликата курьера")
    public void testCreateDuplicateCourier() {
        String login = "login_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "pass2", "Ivan");
        courier.create()
                .then()
                .statusCode(201);

        courier.create()
                .then()
                .statusCode(409)
                .body("message", containsString("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Ошибка создания курьера без пароля")
    public void testCreateCourierMissingPassword() {
        Courier courier = new Courier("login_" + System.currentTimeMillis(), null, "Ivan");

        courier.create()
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Ошибка создания курьера без логина")
    public void testCreateCourierMissingLogin() {
        Courier courier = new Courier(null, "pass3", "Ivan");

        courier.create()
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void testLoginCourierSuccess() {
        Courier courier = createCourierWithUniqueLogin("pass1", "Ivan");
        assertTrue(courierId > 0);
    }

    @Test
    @DisplayName("Ошибка авторизации с неправильным логином")
    public void testLoginCourierWrongLogin() {
        createCourierWithUniqueLogin("pass5", "Ivan");

        Courier.login("wronglogin", "pass5")
                .then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка авторизации с неправильным паролем")
    public void testLoginCourierWrongPassword() {
        Courier courier = createCourierWithUniqueLogin("pass5", "Ivan");

        Courier.login(courier.getLogin(), "wrongpass")
                .then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка авторизации без логина")
    public void testLoginCourierMissingLogin() {
        Courier.login(null, "pass")
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка авторизации без пароля")
    public void testLoginCourierMissingPassword() {
        Courier courier = createCourierWithUniqueLogin("pass5", "Ivan");

        Courier.login(courier.getLogin(), null)
                .then()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка авторизации под несуществующим пользователем")
    public void testLoginNonExistentCourier() {
        Courier.login("nonexistentlogin", "anypass")
                .then()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }
}