import pojo.Courier;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.net.HttpURLConnection;

public class CreateCourierTest {
    private final Courier courier = new Courier("cadzua", "123456", "misima");
    private final CourierMethods courierMethods = new CourierMethods();

    @Before
    @Step("Предусловия для создания теста")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.basePath = "/api/v1/courier";
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Тест на создание нового курьера")
    public void createCourier(){
        courierMethods.setCourier(courier);
        courierMethods.createCourier().then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .and()
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание дубля курьера")
    @Description("Тест на создание дубля курьера")
    public void createDublicateCourier() {
        courierMethods.setCourier(courier);
        courierMethods.createCourier().then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED);
        courierMethods.createCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Тест на создание курьера без логина")
    public void createCourierWithoutLogin() {
        courierMethods.setCourier(new Courier("", "123456", "misima"));
        courierMethods.createCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Тест на создание курьера без пароля")
    public void createCourierWithoutPassword() {
        courierMethods.setCourier(new Courier("123", "", "Alex"));
        courierMethods.createCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    @Step("Удаление курьера")
    public void deleteTestData() {
        courierMethods.deleteCourier();
    }

}
