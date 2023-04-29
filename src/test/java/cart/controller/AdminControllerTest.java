package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import cart.dto.ProductCreateRequest;
import cart.dto.ProductUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/dropTable.sql", "/data.sql"})
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
    @DisplayName("product의 이름이 스무글자가 넘으면 400을 응답한다.")
    void saveFailProductName() {
        final ExtractableResponse<Response> response = saveProduct("치킨치킨치킨치킨치킨치킨치킨치킨치킨치킨치킨", 10000, "img");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest(name = "product의 가격이 10원이상 1,000,000 이하가 아니면 예외가 발생한다.")
    @ValueSource(ints = {9, 0, -1, 1_000_001})
    void saveFailProductPrice(int price) {
        final ExtractableResponse<Response> response = saveProduct("치킨", price, "img");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("상품을 수정한다")
    void updateProduct() {
        // given
        saveProduct("치킨", 10000, "img");
        final ProductUpdateRequest request = new ProductUpdateRequest("샐러드", 20000, "changedImg");

        // when
        final ExtractableResponse<Response> response = given()
                .body(request)
                .when()
                .put("/admin/product/{id}", 1L)
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

    @Test
    @DisplayName("id가 존재하지 않는 경우 400을 응답한다.")
    void deleteFailProduct() {
        final ExtractableResponse<Response> response = given()
                .when()
                .delete("/admin/product/{id}", 1L)
                .then().log().all()
                .extract();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.jsonPath().getString("message")).isEqualTo("존재하지 않는 상품입니다.")
        );
    }

    private ExtractableResponse<Response> saveProduct(final String name, final int price, final String img) {
        final ProductCreateRequest request = new ProductCreateRequest(name, price, img);

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
