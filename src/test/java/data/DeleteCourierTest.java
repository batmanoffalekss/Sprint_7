package data;

import base.BaseCourierTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static base.DataTest.NON_EXISTENT_ID_COURIER;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.deleteCourier;
import static steps.CourierSteps.deleteCourierWithoutId;

public class DeleteCourierTest extends BaseCourierTest {

    @Test
    @DisplayName("Удаление курьера")
    @Description("Успешное удаление курьера по id")
    public void testSuccessDeleteCourier(){
        deleteCourier(courierId)
                .then()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    @Description("Проверка ошибки удаление курьера без id")
    public void testDeleteCourierWithoutId() {
        deleteCourierWithoutId()
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Удаление несуществующего курьера")
    @Description("Проверка ошибки удаление несуществующего курьера")
    public void testDeleteCourierNonExistentId(){
        deleteCourier(NON_EXISTENT_ID_COURIER)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Курьера с таким id нет."));
    }
}
