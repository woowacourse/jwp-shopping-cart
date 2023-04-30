package cart.controller;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ViewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("인덱스 페이지를 잘 호출하는지 확인한다.")
    void productList() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("상품목록"));
    }

    @Test
    @DisplayName("관리자 페이지를 잘 호출하는지 확인한다.")
    void admin() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE product");
    }

}
