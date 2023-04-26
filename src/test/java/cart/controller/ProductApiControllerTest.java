package cart.controller;

import static org.hamcrest.core.Is.is;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import cart.dto.ProductDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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

    @DisplayName("상품 등록 테스트")
    @Test
    void insert() {
        ProductDto productDto = new ProductDto("name", "image", 1000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("http://localhost:" + port + "/product/insert")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(is("ok"));
    }

    @DisplayName("상품 등록 잘못된 요청 시 예외 테스트")
    @Test
    void validateInsertRequest() {
        ProductDto productDto = new ProductDto("", "image", 1000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("http://localhost:" + port + "/product/insert")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(is("name을 다시 입력해주세요."));
    }

    @DisplayName("상품 수정 테스트")
    @Test
    void update() {
        // given
        ProductEntity productEntity = new ProductEntity("name", "image", 1000);
        int createdProductId = productDao.insert(productEntity);

        ProductDto productDto = new ProductDto("name", "changeImage", 2000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("http://localhost:" + port + "/product/update/" + createdProductId)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body(is(String.valueOf(createdProductId)));
    }

    @DisplayName("상품 수정 잘못된 요청 시 예외 테스트")
    @Test
    void validateUpdateRequest() {
        // given
        ProductEntity productEntity = new ProductEntity("name", "image", 1000);
        int createdProductId = productDao.insert(productEntity);

        ProductDto productDto = new ProductDto("", "changeImage", 2000);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productDto)
                .when().post("http://localhost:" + port + "/product/update/" + createdProductId)
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
