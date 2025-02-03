import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderMethods {

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ")
    public Response createOrder() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post();
    }

    @Step("Получить список заказов")
    public Response getOrder() {
        return given().log().all().get();
    }
}
