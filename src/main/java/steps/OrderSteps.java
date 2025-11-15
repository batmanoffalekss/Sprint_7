package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static constants.Endpoint.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response makeOrder(OrderModel orderModel){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Получение списка заказа")
    public static Response getListOrder(){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(LIST_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получить трек заказа")
    public static Integer getTrackNumber(Response trackResponse){
        return trackResponse
                .jsonPath()
                .getInt("track");
    }

    @Step("Получить номер заказа")
    public static Integer getOrderId(Response orderResponse){
        return orderResponse
                .jsonPath()
                .getInt("order.id");
    }

    @Step("Отмена заказа")
    public static void cancelOrder(int trackNumber) {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .put(CANCEL_ORDER_PATH + "/" + trackNumber)
                .then()
                .extract().response();
    }

    @Step("Принятие заказа курьером")
    public static Response acceptOrder(Integer courierId, Integer orderId){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER_PATH + "/" + orderId)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Получение заказа по номеру")
    public static Response getOrderByTrack(Integer track){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .queryParam("t", track)
                .when()
                .get(ORDER_TRACK_PATH)
                .then()
                .log().all()
                .extract().response();
    }
}
