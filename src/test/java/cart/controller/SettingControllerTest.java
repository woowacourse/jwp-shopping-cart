package cart.controller;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SettingControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("세팅 페이지 접속")
    @Test
    void getSettingPage() {
        ValidatableResponse response = RestAssured.given().log().all()
                .when().get("/settings")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.TEXT_HTML_VALUE);
    }
}
