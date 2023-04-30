package cart.controller;

import static org.hamcrest.Matchers.is;

import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
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
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sql/initProducts.sql"})
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto("밋엉", 10000, "미성씨");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productAddRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품을 수정한다.")
    void modifyProduct() {
        ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto("샐러드", 10000, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productModifyRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/products/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 수정하면 예외가 발생한다.")
    void validateModifyNonExistProduct() {
        ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto("샐러드", 10000, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productModifyRequestDto)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/products/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("존재하지 않는 id 입니다."));
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void removeProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제하면 예외가 발생한다.")
    void validateRemoveNonExistProduct() {
        RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/products/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("존재하지 않는 id 입니다."));
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE product");
    }

}
