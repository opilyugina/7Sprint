package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    private final List<String> colors;
    private Integer trackId;

    public OrderCreateTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {null}
        });
    }

    @Test
    @DisplayName("Можно создать заказ с разными цветами")
    @Description("Проверяет, что можно создать заказ: BLACK/GREY, BLACK AND GREY, null (201 Created и номер отслеживания).")
    public void canCreateOrderWithDifferentColors() {
        Order order = generateRandomOrder(colors);

        trackId = orderService.createOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue())
                .extract().path("track");
    }

    @After
    public void cancelOrder() {
        if (trackId != null) {
            orderService.cancelOrder(trackId);
        }
    }
}