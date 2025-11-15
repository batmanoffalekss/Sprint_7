package data;

import base.BaseCourierTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import org.junit.Test;

import static base.DataTest.FIRST_NAME;
import static base.DataTest.PASSWORD;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CreateCourierTest extends BaseCourierTest {

    @Test
    @DisplayName("Создание курьера при заполнении всех обязательных полей")
    @Description("Успешное создание курьера возвращает код 201")
    public void testCreateCourierSuccess() {
        createResponse
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Создание второго курьера с одними данными возвращает код ошибки 409")
    public void testCreateDuplicateCourier() {
        Response duplicateCourier = createCourier(courier);
        duplicateCourier
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message",
                        equalTo("Этот логин уже используется."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера без логина выдает ошибку 400")
    public void createCourierWithoutLoginTest(){
        courier.setLogin(null);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера без пароля выдает ошибку 400")
    public void createCourierWithoutPasswordTest(){
        courier.setPassword(null);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Создание курьера без логина и пароля выдает ошибку 400")
    public void createCourierWithoutPasswordAndLoginTest(){
        courier.setPassword(null);
        courier.setLogin(null);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Создание курьера с уже существующим логином возвращает код ошибки 409")
    public void testCreateCourierWithExistingLogin() {
        CourierModel courierWithExistingLogin =
                new CourierModel(courier.getLogin(), PASSWORD, FIRST_NAME);
        Response existingLoginResponse = createCourier(courierWithExistingLogin);
        existingLoginResponse
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message",
                        equalTo("Этот логин уже используется."));
    }

}
