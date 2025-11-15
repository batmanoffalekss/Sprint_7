package base;

import com.github.javafaker.Faker;
import model.OrderModel;

import java.util.List;

public class DataTest {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    static Faker user = new Faker();
    public static final String LOGIN = user.name().lastName()+user.number().digits(2);
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRST_NAME = user.name().firstName();

    public static final String INCORRECT_LOGIN = "Йорик";
    public static final String INCORRECT_PASSWORD = "12345";

    public static final int NON_EXISTENT_ID_COURIER = user.random().nextInt(1000000,2000000);
    public static final int NON_EXISTENT_ID_ORDER = user.random().nextInt(1000000,2000000);

    public static OrderModel getBaseOrder(){
        return new OrderModel(
                user.name().firstName(),
                user.name().lastName(),
                user.address().streetAddress(),
                String.valueOf(user.number().numberBetween(1, 10)),
                user.phoneNumber().phoneNumber(),
                "2024-12-12",
                user.number().numberBetween(1, 8),
                List.of("BLACK"),
                user.lorem().sentence(3, 5)
        );
    }
}
