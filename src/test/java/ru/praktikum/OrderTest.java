package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.*;

import static org.hamcrest.Matchers.*;

public class OrderTest extends BaseTest {

    @Test
    @DisplayName("При заказе можно указать черный цвет")
    public void testCreateOrderWithBlackColor() {
        Order order = createOrder(Collections.singletonList("BLACK"));
        order.create()
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("При заказе можно указать серый цвет")
    public void testCreateOrderWithGreyColor() {
        Order order = createOrder(Collections.singletonList("GREY"));
        order.create()
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("При заказе можно указать оба цвета")
    public void testCreateOrderWithBothColors() {
        Order order = createOrder(Arrays.asList("BLACK", "GREY"));
        order.create()
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("При заказе можно не указывать цвет")
    public void testCreateOrderWithNoColor() {
        Order order = createOrder(null);
        order.create()
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Успешно получен список заказов")
    public void testGetOrdersList() {
        Order.getOrders()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}