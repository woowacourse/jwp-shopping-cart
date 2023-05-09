package cart.cart.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;

@Sql("/testdata.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartViewControllerTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    void 장바구니_페이지_조회() {
        given().when()
                .log().all()
                .get("cart")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @Test
    void 개별_장바구니_조회() {
        given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 장바구니_상품_추가() {
        given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts?productId=1")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 장바구니_상품_삭제() {
        given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().delete("carts/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .contentType(ContentType.HTML);
    }
}