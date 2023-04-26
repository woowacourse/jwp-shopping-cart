package cart;

import cart.dto.request.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProductIntergrationTest {

    private static final ProductRequest PRODUCT_REQUEST = new ProductRequest("test", 1000, "testUrl");

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품을 정상적으로 생성한다")
    @Test
    void create_product() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(PRODUCT_REQUEST)
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("상품 리스트를 정상적으로 불러온다.")
    void get_products() {
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품을 정상적으로 수정한다.")
    void update_product() {
        ProductRequest updateRequest = new ProductRequest("updateName", 2000, "updateUrl");

        ResponseBody body = getCreateResponse();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRequest)
                .when().put("/admin/" + body.asString())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private ResponseBody getCreateResponse() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(PRODUCT_REQUEST)
                .when().post("/admin")
                .body();
    }

    @Test
    @DisplayName("상품을 정상적으로 삭제한다.")
    void delete_product() {
        ResponseBody body = getCreateResponse();

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/admin/" + body.asString())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
