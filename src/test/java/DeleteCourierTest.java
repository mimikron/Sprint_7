import pojo.Courier;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.net.HttpURLConnection;

public class DeleteCourierTest {

    private final Courier courier = new Courier("cadzua", "123456", "misima");
    private final CourierMethods courierMethods = new CourierMethods();

    @Before
    @Step("Предусловия для создания теста")
    public void setUp() {
        RestAssured.basePath = "/api/v1/courier/";
    }

    @Test
    @DisplayName("Неуспешный запрос удаления курьера")
    @Description("Тест неуспешный запрос удаления курьера")
    public void deleteCourierFail(){
        courierMethods.deleteCourier("00000")
                .then().log().all()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Курьера с таким id нет."));
    }

    @Test
    @DisplayName("Успешный запрос удаления курьера")
    @Description("Тест успешный запрос удаления курьера")
    public void deleteCourierSuccess(){
        courierMethods.setCourier(courier);
        courierMethods.createCourier().then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("ok", is(true));

        courierMethods.deleteCourier(courierMethods.loginCourier()
                        .then().log().all()
                        .extract().path("id").toString()).then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .and()
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Запрос без id курьера")
    @Description("Тест запрос без id  курьера")
    public void deleteCourierWithoutId(){
        courierMethods.deleteCourier("").then().log().all()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

}
