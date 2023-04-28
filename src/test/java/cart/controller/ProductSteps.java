package cart.controller;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SuppressWarnings("NonAsciiCharacters")
abstract class ProductSteps {

    @BeforeEach
    void setup(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    ExtractableResponse<Response> 치킨을_저장한다() {
        return 상품을_저장한다("치킨", 10000, "img");
    }

    ExtractableResponse<Response> 상품을_저장한다(final String name, final int price, final String img) {
        final ProductRequest request = new ProductRequest(name, price, img);

        return given()
                .body(request)
                .when()
                .post("/admin/product")
                .then().log().all()
                .extract();
    }

    ExtractableResponse<Response> 상품을_수정한다(final ProductRequest request, final Long id) {
        return given()
                .body(request)
                .when()
                .put("/admin/product/{id}", id)
                .then().log().all()
                .extract();
    }

    ExtractableResponse<Response> 상품을_삭제한다(final Long id) {
        return given()
                .when()
                .delete("/admin/product/{id}", id)
                .then().log().all()
                .extract();
    }

    RequestSpecification given() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
