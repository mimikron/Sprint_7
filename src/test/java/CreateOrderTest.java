import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.Order;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class CreateOrderTest {
    Order order;
    OrderMethods orderMethods = new OrderMethods();

    public CreateOrderTest(String[] color) {
        this.order = new Order("Vinny", "Puh", "Zeleniy Les, 69", "45", "015-111-14", 6, "2025-10-13", "Very nice day");
        this.order.setColor(color);
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.basePath = "/api/v1/orders";
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"BLACK, GREY"}},
                {new String[]{}},
                {new String[]{"GREY"}}
        };
    }

    @Test
    @DisplayName("Успешное создание заказа")
    @Description("Тест на создание заказа")
    public void createOrder() {
        orderMethods.setOrder(order);
        orderMethods.createOrder()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("track", notNullValue());
    }

}
