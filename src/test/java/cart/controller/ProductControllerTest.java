package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.ProductRequest;
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
            ProductRequest productRequest = new ProductRequest(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
                    .when()
                        .post("/products")
                    .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value())
                        .body(equalTo("" + 1));
        }

        @DisplayName("상품 가격이 음수일 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_PriceLessThanZero() {
            ProductRequest productRequest = new ProductRequest(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    -10000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
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
            ProductRequest productRequest = new ProductRequest(
                    " ",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
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
            ProductRequest productRequest = new ProductRequest(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );
            productDao.save(productRequest);
        }

        @DisplayName("성공")
        @Test
        void Should_Success_When_UpdateProduct() {
            before();
            ProductRequest productRequest = new ProductRequest(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
                    .when()
                        .post("/products/{id}", 1)
                    .then()
                        .log().all()
                        .statusCode(HttpStatus.OK.value());

            ProductDto product = productDao.findAll().get(0);

            assertThat(product.getId()).isEqualTo(1);
            assertThat(product.getName()).isEqualTo("토리");
            assertThat(product.getPrice()).isEqualTo(20000);
        }

        @DisplayName("실패")
        @Test
        void Should_Exception_When_UpdateProduct() {
            ProductRequest productRequest = new ProductRequest(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
                    .when()
                        .post("/products/{id}", 1)
                    .then()
                        .log().all()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .body(equalTo("해당하는 ID가 없습니다."));
        }

        @DisplayName("상품 가격이 음수일 경우 예외가 발생한다.")
        @Test
        void Should_Exception_When_PriceLessThanZero() {
            before();
            ProductRequest productRequest = new ProductRequest(
                    "토리",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    -20000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
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
            ProductRequest productRequest = new ProductRequest(
                    " ",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    20000
            );

            RestAssured
                    .given()
                        .log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(productRequest)
                    .when()
                        .post("/products")
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
            ProductRequest productRequest = new ProductRequest(
                    "오잉",
                    "https://pelicana.co.kr/resources/images/menu/original_menu01_200529.png",
                    10000
            );
            productDao.save(productRequest);
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
                        .statusCode(HttpStatus.OK.value());

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
