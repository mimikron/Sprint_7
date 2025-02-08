import client.Client;
import pojo.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierMethods extends Client {

    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создание курьера")
    public Response createCourier() {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post();
    }

    @Step("Авторизация курьера")
    public Response loginCourier() {
        return given().log().all()
                .spec(getSpec())
                .body(courier)
                .when()
                .post("/login");
    }

    @Step("Удаление курьера")
    public void deleteCourier() {
        Integer courierId = loginCourier()
                .then().log().all()
                .extract()
                .path("id");
        given().log().all().spec(getSpec()).delete("/" + courierId);
    }

    @Step("Удаление курьера по id")
    public Response deleteCourier(String courierId) {
        if(courierId.isEmpty()) {
            return given().log().all().spec(getSpec()).delete();
        } else {
            return given().log().all().spec(getSpec()).delete(courierId);
        }
    }
}
