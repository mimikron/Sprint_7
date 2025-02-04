import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;

public class GetOrderTest {
    OrderMethods orderMethods = new OrderMethods();

    @Before
    public void setUp() {
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
                .body("orders", not(emptyOrNullString()));

    }
}
