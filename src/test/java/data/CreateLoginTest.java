package data;

import base.BaseCourierTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static base.DataTest.INCORRECT_LOGIN;
import static base.DataTest.INCORRECT_PASSWORD;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.CourierSteps.loginCourier;

public class CreateLoginTest extends BaseCourierTest {

    @Test
    @DisplayName("Авторизация курьера при заполнении всех обязательных полей")
    @Description("Успешная авторизация курьера возвращает код 200")
    public void testLoginSuccess() {
        loginCourier(courier)
                .then()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Возвращает код ошибки 400")
    public void testLoginWithoutLogin() {
        courier.setLogin(null);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Возвращает код ошибки 400")
    public void testLoginWithoutPassword(){
        courier.setPassword(null);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина и пароля")
    @Description("Авторизация курьера без логина и пароля выдает ошибку 400")
    public void testLoginWithoutPasswordAndLogin(){
        courier.setPassword(null);
        courier.setLogin(null);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Авторизация c неверным логином")
    @Description("Возвращает код ошибки 404")
    public void testLoginWithIncorrectLogin() {
        courier.setLogin(INCORRECT_LOGIN);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    @Description("Возвращает код ошибки 404")
    public void testLoginWithIncorrectPassword() {
        courier.setLogin(INCORRECT_PASSWORD);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация c неверным логином и паролем")
    @Description("Авторизация с неверным логином и паролем выдает ошибку 404")
    public void testLoginWithIncorrectPasswordAndLogin (){
        courier.setPassword(INCORRECT_PASSWORD);
        courier.setLogin(INCORRECT_LOGIN);
        loginCourier(courier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message",
                        equalTo("Учетная запись не найдена"));
    }
}
