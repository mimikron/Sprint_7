import client.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderMethods extends Client {

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ")
    public Response createOrder() {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post();
    }

    @Step("Получить список заказов")
    public Response getOrder() {
        return given().log().all().spec(getSpec()).get();
    }
}
