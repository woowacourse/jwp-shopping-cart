package cart.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;

import cart.dto.ProductRequestDto;
import cart.entity.Product;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:sql/initProducts.sql"})
public class AdminControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("관리자 페이지 호출 확인")
    void admin() {
        RestAssured.given().log().all()
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @Test
    @DisplayName("상품 추가 확인")
    void addProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("밋엉", 10000, "미성씨");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

    @Test
    @DisplayName("상품 수정 확인")
    void modifyProduct() {
        ProductRequestDto productRequestDto = new ProductRequestDto("샐러드", 10000, "밋밋엉");

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .accept(MediaType.TEXT_HTML_VALUE)
                .when().put("/admin/products/2")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("<title>관리자 페이지</title>"));
    }

}
