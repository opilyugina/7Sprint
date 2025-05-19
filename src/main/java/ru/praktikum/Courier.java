package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this(login, password, null);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    @Step("Создание курьера")
    public Response create() {
        return given()
                .header("Content-type", "application/json")
                .body(this)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public static Response login(String login, String password) {
        return given()
                .header("Content-type", "application/json")
                .body(new Courier(login, password))
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера по ID")
    public static Response delete(int id) {
        return given()
                .when()
                .delete("/api/v1/courier/" + id);
    }
}