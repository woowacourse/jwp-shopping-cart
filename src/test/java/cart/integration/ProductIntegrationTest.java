package cart.integration;

import cart.dto.request.ProductRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 추가 성공")
    void create_success() {
        final ProductRequestDto requestDto = new ProductRequestDto("name", "name.jpg", 1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/products")
                .then().statusCode(HttpStatus.CREATED.value())
                .headers("Location", "/admin");
    }

    @Test
    @DisplayName("상품 추가 실패 - null이 존재하는 경우")
    void create_fail_include_null() {
        final ProductRequestDto requestDto = new ProductRequestDto(null, "name.jpg", 1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/products")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 추가 실패 - 가격이 음수인 경우")
    void create_fail_negative_price_value() {
        final ProductRequestDto requestDto = new ProductRequestDto("ditoo", "ditoo.jpg", -1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/products")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 수정 성공")
    @Sql("/product_dummy_data.sql")
    void update_success() {
        final ProductRequestDto requestDto = new ProductRequestDto("name", "name.jpg", 1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .pathParam("id", 1)
                .when().patch("/products/{id}")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품 수정 실패 - null이 존재하는 경우")
    @Sql("/product_dummy_data.sql")
    void update_fail_include_null() {
        final ProductRequestDto requestDto = new ProductRequestDto("name", null, 1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .pathParam("id", 1)
                .when().patch("/products/{id}")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 수정 실패 - 가격이 음수인 경우")
    @Sql("/product_dummy_data.sql")
    void update_fail_negative_price_value() {
        final ProductRequestDto requestDto = new ProductRequestDto("name", "image.png", -1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .pathParam("id", 1)
                .when().patch("/products/{id}")
                .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품 수정 실패 - 존재하지 않는 상품인 경우")
    @Sql("/product_dummy_data.sql")
    void update_fail_product_not_found() {
        final ProductRequestDto requestDto = new ProductRequestDto("name", "image.png", 1000);

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .pathParam("id", 0)
                .when().patch("/products/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("상품 삭제 성공")
    @Sql("/product_dummy_data.sql")
    void delete_success() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .when().delete("/products/{id}")
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품 삭제 실패 - 존재하지 않는 상품인 경우")
    @Sql("/product_dummy_data.sql")
    void delete_fail_product_not_found() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 0)
                .when().delete("/products/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }
}
