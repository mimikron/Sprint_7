import io.qameta.allure.Step;
import org.junit.After;
import pojo.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
public class LoginCourierTest {

    Courier courier = new Courier("cadzua", "123456", "misima");
    CourierMethods courierMethods = new CourierMethods();

    @Before
    public void setUp() {
        RestAssured.basePath = "/api/v1/courier";
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Тест на авторизацию курьера")
    public void loginCourier() {
        courierMethods.setCourier(courier);
        courierMethods.createCourier();
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Тест на авторизацию курьера без логина")
    public void loginCourierWithoutLogin() {
        courierMethods.setCourier(new Courier("", "123"));
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Тест на авторизацию курьера без пароля")
    public void loginCourierWithoutPassword() {
        courierMethods.setCourier(new pojo.Courier("Login", ""));
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Тест на авторизацию несуществующего курьера")
    public void loginUnknownCourier() {
        courierMethods.setCourier(courier);
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация c неправильным логином")
    @Description("Тест на авторизацию с неправильным логином")
    public void loginCourierWithWrongLogin() {
        courierMethods.setCourier(courier);
        courierMethods.createCourier();
        courierMethods.setCourier(new pojo.Courier("heihachi", "123456"));
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        courierMethods.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация c неправильным паролем")
    @Description("Тест на авторизацию с неправильным паролем")
    public void loginCourierWithWrongPassword() {
        courierMethods.setCourier(courier);
        courierMethods.createCourier();
        courierMethods.setCourier(new pojo.Courier("Admin_Login123", "123"));
        courierMethods.loginCourier()
                .then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
        courierMethods.setCourier(courier);
    }

    @After
    @Step("Удаление курьера")
    public void deleteTestData() {
        courierMethods.deleteCourier();
    }
}
