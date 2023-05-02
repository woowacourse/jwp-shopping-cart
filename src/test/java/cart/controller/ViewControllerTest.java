package cart.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ViewControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/ 를 통해, 상품 목록 페이지를 반환받는다. (index.html)")
    void productList() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>상품목록</title>"));
    }

    @Test
    @DisplayName("/admin 을 통해, 관리자 페이지를 반환받는다. (admin.html)")
    void admin() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

}
