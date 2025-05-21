package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest extends BaseTest {

    @Test
    @DisplayName("Успешно получен список заказов")
    @Description("Проверяет получение списка заказов (200 ОК).")
    public void testGetOrdersList() {
        orderService.getOrders()
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}