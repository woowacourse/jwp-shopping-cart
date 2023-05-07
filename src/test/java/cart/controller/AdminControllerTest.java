package cart.controller;

import cart.dto.CreateProductRequest;
import cart.dto.UpdateProductRequest;
import cart.service.ProductService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql("/init.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

    @Autowired
    private ProductService productService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void 어드민_페이지를_조회한다() {
        RestAssured
                .given()
                .when().get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 유효한_추가_요청이라면_201_응답코드를_반환한다() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateProductRequest("name", "imageUrl", 1000))
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 유효하지_않은_추가_요청이라면_400_응답코드를_반환한다() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateProductRequest("name", "imageUrl", -100))
                .when().post("/admin/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 유효한_수정_요청이라면_201_응답코드를_반환한다() {
        productService.addProduct(new CreateProductRequest("name", "imageUrl", 1000));

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateProductRequest("name", "imageUrl", 50000))
                .when().patch("/admin/products/" + 1L)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 유효하지_않은_수정_요청이라면_400_응답코드를_반환한다() {
        productService.addProduct(new CreateProductRequest("name", "imageUrl", 1000));

        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateProductRequest("name", "imageUrl", -100))
                .when().patch("/admin/products/" + 1L)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 존재하지_않는_상품에_대한_수정_요청이라면_404_응답코드를_반환한다() {
        RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateProductRequest("name", "imageUrl", 50000))
                .when().patch("/admin/products/" + 123L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 유효한_삭제_요청이라면_200_응답코드를_반환한다() {
        productService.addProduct(new CreateProductRequest("name", "imageUrl", 1000));

        RestAssured
                .given()
                .when().delete("/admin/products/" + 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 존재하지_않는_상품에_대한_삭제_요청이라면_404_응답코드를_반환한다() {
        RestAssured
                .given()
                .when().delete("/admin/products/" + 1L)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
