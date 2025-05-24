package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierService {

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(Endpoints.COURIER);
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierModel model) {
        return given()
                .header("Content-type", "application/json")
                .body(model)
                .when()
                .post(Endpoints.COURIER_LOGIN);
    }

    @Step("Удаление курьера по ID")
    public Response deleteCourier(int id) {
        return given()
                .when()
                .delete(Endpoints.COURIER_DELETE + id);
    }
}