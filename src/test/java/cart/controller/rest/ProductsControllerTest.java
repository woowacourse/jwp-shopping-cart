package cart.controller.rest;

import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("post 정상적인 request를 전송하면 created 상태코드를 반환한다")
    void createTest() {
        ProductRequest productRequest = new ProductRequest("테스트", BigDecimal.valueOf(1000), "http://testtest");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("post 비정상적인 request를 전송하면 bad_request 상태코드를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void createExceptionTest() {
        ProductRequest productRequest = new ProductRequest("", null, "");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(3));
    }

    @Test
    @Sql("/dummy-product.sql")
    @DisplayName("put 정상적인 request를 전송하면 no_content 상태코드를 반환한다")
    void updateTest() {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(1L, "테스트", BigDecimal.valueOf(10000), "http://testtest");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Sql("/dummy-product.sql")
    @DisplayName("put 비정상적인 request를 전송하면 bad_request 상태코드를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void updateExceptionTest() {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(null, "", null, "");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/products/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(4));
    }

    @Test
    @Sql("/dummy-product.sql")
    @DisplayName("delete 정상적인 request를 전송하면 no_content 상태코드를 반환한다")
    void deleteTest() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
