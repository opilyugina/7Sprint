package ru.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderService {

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(Endpoints.ORDERS);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given().get(Endpoints.ORDERS);
    }

    @Step("Отмена заказа")
    public Response cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .body("{\"track\":" + track + "}")
                .when()
                .put(Endpoints.ORDER_CANCEL);
    }
}