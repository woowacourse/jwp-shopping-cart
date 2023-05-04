package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private static final String EXITING_USER_EMAIL = "pooh@naver.com";
    private static final String USER_PASSWORD = "123";
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("로그인하지 않은 유저가 addProduct 요청을 보낸다면 401 status code 를 반환한다")
    @Test
    void addProduct_invalid_notAuthorized() {
        //given,when,then
        RestAssured
                .given().log().all()
                .when().post("/cart")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 요청에 해당하는 유저가 없으면 예외가 발생한다")
    @Test
    void addProduct_invalid_notExistingUser() {
        //given,when,then
        RestAssured
                .given().log().all()
                .auth().preemptive().basic("notExisingUserEmail", "None")
                .when().post("/cart")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("로그인 정보가 유효하다면 상품 추가 요청을 완료한다")
    @Test
    void addProduct_valid() {
        //given,when,then
        RestAssured
                .given().log().all()
                .auth().preemptive().basic(EXITING_USER_EMAIL, USER_PASSWORD)
                .when().post("/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
