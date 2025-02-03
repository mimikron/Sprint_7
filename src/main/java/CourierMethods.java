import pojo.Courier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierMethods {

    private Courier courier;

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Step("Создание курьера")
    public Response createCourier() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post();
    }

    @Step("Авторизация курьера")
    public Response loginCourier() {
        return given().log().all()
                .contentType(ContentType.JSON)
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
        given().log().all().delete("/" + courierId);
    }

    @Step("Удаление курьера по id")
    public Response deleteCourier(String courierId) {
        if(courierId.isEmpty()) {
            return given().log().all().delete();
        } else {
            return given().log().all().delete(courierId);
        }
    }
}
