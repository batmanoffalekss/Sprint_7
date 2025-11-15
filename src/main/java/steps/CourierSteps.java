package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import static constants.Endpoint.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Cоздание курьера")
    public static Response createCourier(CourierModel courierModel){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Авторизация курьера")
    public static Response loginCourier(CourierModel courierModel) {
        return given()
                .with()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Получение id курьера")
    public static int getCourierId(CourierModel courierModel){
        Response loginResponse = loginCourier(courierModel);
        return loginResponse
                .jsonPath()
                .getInt("id");
    }

    @Step("Удаление курьера c id")
    public static Response deleteCourier(Integer courierId) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(DELETE_COURIER_PATH + "/" + courierId)
                .then()
                .extract().response();
    }

    @Step("Удаление курьера без id")
    public static Response deleteCourierWithoutId() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(DELETE_COURIER_PATH)
                .then()
                .extract().response();
    }
}