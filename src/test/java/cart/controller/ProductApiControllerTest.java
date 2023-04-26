package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.service.ProductService;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    @Autowired
    ProductService productService;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품 정보를 받아서 상품을 생성한다.")
    void create() {
        final ProductRequestDto productRequestDto = new ProductRequestDto(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
        );

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().post("/products")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", "/products/1");
    }

    @Test
    @DisplayName("상품의 정보를 수정하여 상품을 업테이트한다.")
    void update() {
        //given
        final ProductRequestDto productRequestDto = new ProductRequestDto(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
        );
        final Long registeredId = productService.register(productRequestDto);
        final ProductRequestDto updatedProductRequestDto = new ProductRequestDto(
            "스플릿2",
            "스프링2",
            150000,
            "우아한테크코스2",
            List.of(3L, 4L)
        );

        //when
        //then
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(updatedProductRequestDto)
            .when().put("/products/" + registeredId)
            .then()
            .statusCode(HttpStatus.OK.value());
    }
}
