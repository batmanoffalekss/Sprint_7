package data;

import base.BaseFullTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static base.DataTest.NON_EXISTENT_ID_ORDER;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getOrderByTrack;

public class GetOrderByTrackTest extends BaseFullTest {

    @Test
    @DisplayName("Успешное получение заказа по его id номеру")
    @Description("Успешный запрос возвращает объект с заказом и код 200")
    public void testGetOrderByTrackSuccess() {
        getOrderByTrack(track)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Получение заказа без id номера")
    @Description("Запрос без номера возвращает код ошибки 400")
    public void testGetWithoutTrack() {
        getOrderByTrack(null)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Получение заказа с несуществующим id номером")
    @Description("Запрос с несуществующим номером возвращает код ошибки 400")
    public void testGetWithNonExistentTrack() {
        getOrderByTrack(NON_EXISTENT_ID_ORDER)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Заказ не найден"));
    }
}
