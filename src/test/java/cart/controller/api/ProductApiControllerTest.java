package cart.controller.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductCategoryDto;
import cart.dto.request.ProductRequestDto;
import cart.entity.category.CategoryEntity;
import cart.service.ProductService;
import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
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
        //given
        final ProductRequestDto productRequestDto = new ProductRequestDto(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
        );

        //when
        //then
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
        final Long registerProductId = registerProduct();

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
            .when().put("/products/" + registerProductId)
            .then()
            .statusCode(HttpStatus.OK.value());

        final ProductCategoryDto updatedProduct = productService.findAll().get(0);

        assertAll(
            () -> assertThat(updatedProduct.getName()).isEqualTo("스플릿2"),
            () -> assertThat(updatedProduct.getImageUrl()).isEqualTo("스프링2"),
            () -> assertThat(updatedProduct.getPrice()).isEqualTo(150000),
            () -> assertThat(updatedProduct.getDescription()).isEqualTo("우아한테크코스2"),
            () -> {
                final List<Long> categoryIds = updatedProduct.getCategoryEntities().stream()
                    .map(CategoryEntity::getId)
                    .collect(Collectors.toList());
                assertThat(categoryIds).containsExactly(3L, 4L);
            }
        );
    }

    private Long registerProduct() {
        final ProductRequestDto productRequestDto = new ProductRequestDto(
            "스플릿",
            "스프링",
            15000,
            "우아한테크코스",
            List.of(1L, 2L)
        );
        return productService.register(productRequestDto);
    }

    @Test
    @DisplayName("상품 ID를 통해 상품을 삭제한다.")
    void delete() {
        //given
        final Long registeredId = registerProduct();

        //when
        //then
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/products/" + registeredId)
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Nested
    @DisplayName("상품을 생성시 상품 정보가 유효한지 체크한다.")
    class Create {

        @Test
        @DisplayName("이미지 URL이 null 일 때")
        void nullImageURL() {
            //given
            final ProductRequestDto productRequestDto = new ProductRequestDto(
                "name",
                null,
                15000,
                "우아한테크코스",
                List.of(1L, 2L)
            );

            //when
            //then
            RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("이미지URL은 비어있을 수 없습니다."));
        }

        @Test
        @DisplayName("상품명의 길이가 50자를 초과할 때")
        void nameOverMaxLength() {
            //given
            final ProductRequestDto productRequestDto = new ProductRequestDto(
                "1".repeat(51),
                "imageUrl",
                15000,
                "우아한테크코스",
                List.of(1L, 2L)
            );

            //when
            //then
            RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("상품명은 1이상, 50이하여야 합니다."));
        }

        @Test
        @DisplayName("가격이 0보다 작을 때")
        void priceUnderOne() {
            //given
            final ProductRequestDto productRequestDto = new ProductRequestDto(
                "name",
                "imageUrl",
                -1,
                "우아한테크코스",
                List.of(1L, 2L)
            );

            //when
            //then
            RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("가격은 0원 이상 " + Integer.MAX_VALUE + "원 이하여야 합니다."));
        }

        @Test
        @DisplayName("중복선택한 카테고리에 없음이 존재할 때")
        void categoryEmptyUnique() {
            //given
            final ProductRequestDto productRequestDto = new ProductRequestDto(
                "name",
                "imageUrl",
                1000,
                "우아한테크코스",
                null
            );

            //when
            //then
            RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequestDto)
                .when().post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", containsString("카테고리를 선택해야 합니다."));
        }
    }
}
