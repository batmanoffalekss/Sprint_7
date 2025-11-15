package data;

import base.BaseFullTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static base.DataTest.NON_EXISTENT_ID_COURIER;
import static base.DataTest.NON_EXISTENT_ID_ORDER;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.OrderSteps.acceptOrder;

public class  AcceptOrderTest extends BaseFullTest {

    @Test
    @DisplayName("Успешное принятие заказа курьером")
    @Description("Принят заказ, код 200")
    public void testAcceptOrderSuccess(){
        acceptOrder(courierId, orderId)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Принятие заказа без id курьера")
    @Description("Запрос без id возвращает ошибку 404")
    public void testAcceptOrderWithoutCourierId(){
        acceptOrder(null, orderId)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Принятие заказа с несуществующим id курьера")
    @Description("Запрос без id возвращает ошибку 404")
    public void testAcceptOrderWithNonExistentCourierId(){
        acceptOrder(NON_EXISTENT_ID_COURIER, orderId)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Принятие заказа без номера заказа")
    @Description("Запрос без номера заказа возвращает ошибку 404")
    public void testAcceptOrderWithoutOrderId(){
        acceptOrder(courierId, -1)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Принятие заказа с несуществующим номером заказа")
    @Description("Запрос с несуществующим номером заказа возвращает ошибку 404")
    public void testAcceptOrderWithNonExistentOrderId(){
        acceptOrder(courierId, NON_EXISTENT_ID_ORDER)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Заказа с таким id не существует"));
    }
}