package cart.controller;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PageAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("main 페이지에 접속하면 상태 코드로 200을 반환한다.")
    @Test
    void main_page() {
        ExtractableResponse<Response> result = get("/")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("admin 페이지에 접속하면 상태 코드로 200을 반환한다.")
    @Test
    void admin_page() {
        ExtractableResponse<Response> result = get("/admin")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
