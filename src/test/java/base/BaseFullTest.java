package base;

import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;

import static base.DataTest.getBaseOrder;
import static steps.OrderSteps.*;

public class BaseFullTest extends BaseCourierTest {

    public OrderModel order;
    public Integer track;
    public Integer orderId;
    public Response orderResponse;

    @Before
    public void setupFull() {
        order = getBaseOrder();
        orderResponse = makeOrder(order);

        track = getTrackNumber(orderResponse);
        Response orderByTrack = getOrderByTrack(track);
        orderId = getOrderId(orderByTrack);
    }

    @After
    public void tearDownFull() {
        if (track != null && track != 0) {
            cancelOrder(track);
        }
        super.cleanUp();
    }
}
