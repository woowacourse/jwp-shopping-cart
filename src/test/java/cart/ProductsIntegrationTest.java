package cart;

import static org.hamcrest.core.Is.is;

import cart.domain.product.Product;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.persistence.ProductDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsIntegrationTest {

    @LocalServerPort
    int port;
    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/product에 정상적인 POST request를 전송하면 created 상태코드를 반환한다")
    void createTest() {
        ProductRequest productRequest = new ProductRequest("테스트", 1000, "http://testtest");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("/product에 비정상적인 POST request를 전송하면 bad_request를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void createExceptionTest() {
        ProductRequest productRequest = new ProductRequest("", null, "");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(3));
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/product에 정상적인 PUT request를 전송하면 ok 상태코드를 반환한다")
    void updateTest() {
        ProductDao productDao = applicationContext.getBean("h2ProductDao", ProductDao.class);
        Long createdId = productDao.create(new Product("테스트", 1000, "http://testtest"));
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(
                createdId,
                "수정된 테스트",
                10000,
                "http://testtest/modified"
        );

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/products/" + createdId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("/product에 비정상적인 PUT request를 전송하면 bad_request를 반환하고 에러 메시지의 size는 비정상적인 파라미터의 개수와 같다")
    void updateExceptionTest() {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(null, "", null, "");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productUpdateRequest)
                .when()
                .put("/products/1")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("size()", is(4));
    }

    @Test
    @Sql("/data.sql")
    @DisplayName("/delete에 정상적인 request를 전송하면 no_content 상태코드를 반환한다")
    void deleteTest() {
        ProductDao productDao = applicationContext.getBean("h2ProductDao", ProductDao.class);
        Long createdId = productDao.create(new Product("테스트", 1000, "http://testtest"));

        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/products/" + createdId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
