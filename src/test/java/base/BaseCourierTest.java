package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;

import static base.DataTest.*;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static steps.CourierSteps.*;

public class BaseCourierTest {

    public CourierModel courier;
    public Integer courierId;
    public Response createResponse;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        createResponse = createCourier(courier);
        courierId = getCourierId(courier);
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            Response response = deleteCourier(courierId);
            if (response.statusCode() == HTTP_NOT_FOUND) {
                System.out.println("Курьер уже удален: " + courierId);
            }
        }
    }

}