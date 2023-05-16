package cart.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebMvcConfigurationTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("장바구니 페이지 연동 테스트")
    @Test
    void addViewControllers() {
        RestAssured
                .given().log().all()
                .when().get("/cart")
                .then().log().all()
                .assertThat().statusCode(HttpStatus.OK.value());
    }

    @DisplayName("인증 예외 발생 테스트")
    @Test
    void addInterceptors() {
        RestAssured
                .given().log().all()
                .when().get("/carts/")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }

    @DisplayName("인증 테스트")
    @Test
    void addArgumentResolvers() {
        RestAssured
                .given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }
}
