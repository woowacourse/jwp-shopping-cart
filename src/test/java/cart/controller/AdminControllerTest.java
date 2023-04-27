package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @BeforeEach
    void setup(@LocalServerPort final int port) {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("product를 저장하는 테스트한다.")
    void saveProduct() {
        final ExtractableResponse<Response> response = saveProduct("치킨", 10000, "img");

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).contains("/admin/product/")
        );
    }

    @Test
    @DisplayName("상품을 수정한다")
    void updateProduct() {
        // given
        saveProduct("치킨", 10000, "img");
        final ProductRequest request = new ProductRequest("샐러드", 20000, "changedImg");

        // when
        final ExtractableResponse<Response> response = given()
                .body(request)
                .when()
                .patch("/admin/product/{id}", 1L)
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProduct() {
        saveProduct("치킨", 10000, "img");

        final ExtractableResponse<Response> response = given()
                .when()
                .delete("/admin/product/{id}", 1L)
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private ExtractableResponse<Response> saveProduct(final String name, final int price, final String img) {
        final ProductRequest request = new ProductRequest(name, price, img);

        return given()
                .body(request)
                .when()
                .post("/admin/product")
                .then().log().all()
                .extract();
    }

    private RequestSpecification given() {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
