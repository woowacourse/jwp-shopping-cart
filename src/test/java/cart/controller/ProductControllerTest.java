package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import cart.dto.ProductAddRequestDto;
import cart.dto.ProductModifyRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.execute("DELETE FROM product");
        jdbcTemplate.execute("ALTER TABLE product  ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("상품 저장")
    @Nested
    class 상품_저장_테스트 {
        @DisplayName("성공")
        @Test
        void Should_Success_When_SaveProduct() {
            ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productAddRequestDto)
                    .when()
                    .post("/products")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", "/products/1");

            ProductEntity product = productDao.findById(1).get();
            assertThat(product.getName()).isEqualTo(productAddRequestDto.getName());
            assertThat(product.getImgUrl()).isEqualTo(productAddRequestDto.getImgUrl());
            assertThat(product.getPrice()).isEqualTo(productAddRequestDto.getPrice());

        }

        @DisplayName("상품 가격이 음수일 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_PriceLessThanZero() {
            ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    -10000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productAddRequestDto)
                    .when()
                    .post("/products")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("상품가격은 0 이상이어야 합니다."));
        }

        @DisplayName("상품명이 비어있을 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_NameBlank() {
            ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(
                    " ",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productAddRequestDto)
                    .when()
                    .post("/products")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("상품명은 필수 입력 값입니다."));
        }
    }

    @DisplayName("상품 수정")
    @Nested
    class 상품_수정_테스트 {
        void before() {
            ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );
            productDao.save(new ProductEntity(productAddRequestDto.getName(), productAddRequestDto.getImgUrl(), productAddRequestDto.getPrice()));
        }

        @DisplayName("성공")
        @Test
        void Should_Success_When_UpdateProduct() {
            before();
            ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productModifyRequestDto)
                    .when()
                    .put("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());

            ProductEntity product = productDao.findById(1).get();

            assertThat(product.getName()).isEqualTo(productModifyRequestDto.getName());
            assertThat(product.getImgUrl()).isEqualTo(productModifyRequestDto.getImgUrl());
            assertThat(product.getPrice()).isEqualTo(productModifyRequestDto.getPrice());
        }

        @DisplayName("실패")
        @Test
        void Should_Exception_When_UpdateProduct() {
            ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productModifyRequestDto)
                    .when()
                    .put("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("해당하는 ID가 없습니다."));
        }

        @DisplayName("상품 가격이 음수일 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_PriceLessThanZero() {
            before();
            ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    -20000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productModifyRequestDto)
                    .when()
                    .put("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("상품가격은 0 이상이어야 합니다."));
        }

        @DisplayName("상품명이 비어있을 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_NameBlank() {
            ProductModifyRequestDto productModifyRequestDto = new ProductModifyRequestDto(
                    " ",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productModifyRequestDto)
                    .when()
                    .put("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("상품명은 필수 입력 값입니다."));
        }
    }

    @DisplayName("상품 삭제")
    @Nested
    class 상품_삭제_테스트 {
        void before() {
            ProductAddRequestDto productAddRequestDto = new ProductAddRequestDto(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );
            productDao.save(new ProductEntity(productAddRequestDto.getName(), productAddRequestDto.getImgUrl(), productAddRequestDto.getPrice()));
        }

        @DisplayName("성공")
        @Test
        void Should_Success_When_DeleteProduct() {
            before();

            RestAssured
                    .given()
                    .log().all()
                    .when()
                    .delete("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            assertThat(productDao.findAll().size()).isEqualTo(0);
        }

        @DisplayName("실패")
        @Test
        void Should_Exception_When_DeleteProduct() {
            RestAssured
                    .given()
                    .log().all()
                    .when()
                    .delete("/products/{id}", 1)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(equalTo("해당하는 ID가 없습니다."));
        }
    }
}
