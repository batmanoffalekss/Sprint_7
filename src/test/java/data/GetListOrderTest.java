package data;

import base.BaseOrderTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.getListOrder;

public class GetListOrderTest extends BaseOrderTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в теле ответа возвращается список заказов и код 200")
    public void testGetListOrder() {
        Response response = getListOrder();
        response
                .then()
                .statusCode(HTTP_OK)
                .log().all()
                .body("orders", notNullValue());
    }
}
