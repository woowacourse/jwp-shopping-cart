package cart.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CartControllerTest {

    public static final String EMAIL = "jeoninpyo726@gmail.com";
    public static final String PASSWORD = "a";

    @BeforeEach
    void setup(@LocalServerPort final int port) {
        RestAssured.port = port;

    }

    @Test
    @DisplayName("/cart get요청에 200을 응답한다.")
    void cart() {
        ExtractableResponse<Response> response = RestAssured.
                given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().get("/cart/items")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}