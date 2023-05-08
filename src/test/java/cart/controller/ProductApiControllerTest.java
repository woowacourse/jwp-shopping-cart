package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;

import cart.controller.dto.request.product.ProductInsertRequest;
import cart.controller.dto.request.product.ProductUpdateRequest;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:testData.sql"})
@DisplayName("상품 api 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    ProductDao productDao;

    @BeforeEach
    void initialize() {
        productDao.deleteAll();
    }

    @DisplayName("상품 등록 테스트")
    @Test
    void insert() {
        ProductInsertRequest request = new ProductInsertRequest("name", "http://www.naver.com", 1000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("http://localhost:" + port + "/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        List<ProductEntity> productEntities = productDao.findAll();

        assertThat(productEntities).hasSize(1);
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        // given
        ProductEntity productEntity = new ProductEntity("name", "http://www.naver.com", 1000);
        int createdProductId = productDao.insert(productEntity);

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("name", "http://www.update.com", 2000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when().put("http://localhost:" + port + "/products/" + createdProductId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Optional<ProductEntity> findById = productDao.findById(createdProductId);
        ProductEntity findEntity = findById.get();

        assertThat(findEntity.getId()).isEqualTo(createdProductId);
    }

    @DisplayName("상품 삭제 테스트")
    @Test
    void delete() {
        // given
        ProductEntity productEntity = new ProductEntity("name", "image", 1000);
        int createdProductId = productDao.insert(productEntity);

        RestAssured.given().log().all()
                .when().delete("http://localhost:" + port + "/products/" + createdProductId)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        List<ProductEntity> productEntities = productDao.findAll();

        assertThat(productEntities).hasSize(0);
    }

    @DisplayName("product api 예외 테스트")
    @Nested
    class Validation {

        @DisplayName("상품 등록 잘못된 요청 시(이름) 예외 테스트")
        @Test
        void validateNameInsertRequest() {
            ProductInsertRequest productInsertRequest = new ProductInsertRequest("", "image", 1000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productInsertRequest)
                    .when().post("http://localhost:" + port + "/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 등록 잘못된 요청 시(이미지) 예외 테스트")
        @Test
        void validateImageInsertRequest() {
            ProductInsertRequest productInsertRequest = new ProductInsertRequest("name", "", 1000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productInsertRequest)
                    .when().post("http://localhost:" + port + "/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 등록 잘못된 요청 시(가격) 예외 테스트")
        @ParameterizedTest
        @ValueSource(ints = {99, 10_000_001})
        void validatePriceInsertRequest(final int price) {
            ProductInsertRequest productInsertRequest = new ProductInsertRequest("name", "image", price);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productInsertRequest)
                    .when().post("http://localhost:" + port + "/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 수정 잘못된 요청 시(이름) 예외 테스트")
        @Test
        void validateNameUpdateRequest() {
            // given
            ProductEntity productEntity = new ProductEntity("name", "image", 1000);
            int createdProductId = productDao.insert(productEntity);

            // then
            ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("", "changeImage", 2000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productUpdateRequest)
                    .when().put("http://localhost:" + port + "/products/" + createdProductId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 수정 잘못된 요청 시(이미지) 예외 테스트")
        @Test
        void validateImageUpdateRequest() {
            // given
            ProductEntity productEntity = new ProductEntity("name", "image", 1000);
            int createdProductId = productDao.insert(productEntity);

            // then
            ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest("name", "", 2000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productUpdateRequest)
                    .when().put("http://localhost:" + port + "/products/" + createdProductId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("상품 수정 잘못된 요청 시(가격) 예외 테스트")
        @ParameterizedTest
        @ValueSource(ints = {99, 10_000_001})
        void validatePriceUpdateRequest() {
            // given
            ProductEntity productEntity = new ProductEntity("name", "image", 1000);
            int createdProductId = productDao.insert(productEntity);

            // then
            ProductInsertRequest productInsertRequest = new ProductInsertRequest("name", "", 2000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(productInsertRequest)
                    .when().put("http://localhost:" + port + "/products/" + createdProductId)
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

    }

}
