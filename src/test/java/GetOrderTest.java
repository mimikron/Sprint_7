import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {
    OrderMethods orderMethods = new OrderMethods();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.basePath = "/api/v1/orders";
    }

    @Test
    @DisplayName("Проверка списка заказов")
    @Description("Тест на получение списка заказов")
    public void getOrder() {
        orderMethods.getOrder()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("orders", notNullValue());
    }
}
