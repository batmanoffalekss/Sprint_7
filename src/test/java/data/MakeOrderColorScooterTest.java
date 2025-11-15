package data;

import base.BaseOrderTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class MakeOrderColorScooterTest extends BaseOrderTest {

    private static final List<Integer> tracksToCancel = new ArrayList<>();
    private final List<String> color;

    public MakeOrderColorScooterTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] scooterColor(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK","GREY")},
                {List.of()},
        };
    }

    @Test
    @DisplayName("Создание заказа с разными данными о цвете самоката")
    @Description("Заказ создается вне зависимости от выбора цвета")
    public void makeOrderColorScooterTest(){
        order.setColor(color);
        response = OrderSteps.makeOrder(order);
        response
                .then()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());

        tracksToCancel.add(response.jsonPath().getInt("track"));

    }

    @AfterClass
    public static void cleanupTrack() {
        for (Integer track : tracksToCancel) {
            OrderSteps.cancelOrder(track);
        }
        tracksToCancel.clear();
    }
}
