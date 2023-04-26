package cart.controller;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.AfterTestMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("admin 페이지에 접속하면 상태 코드로 200을 반환한다.")
    @Test
    void admin_page() {
        ExtractableResponse<Response> result = get("/admin")
            .then()
            .extract();

        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("상품을 등록할 때 성공하면 상태코드 200을 그렇지 않으면 상태코드 400을 반환한다.")
    @Rollback
    @MethodSource("createProductAndStatusCode")
    @ParameterizedTest(name = "{displayName}")
    void create_product_success(ProductRequestDto productRequestDto, int statusCode) {
        // when
        ExtractableResponse<Response> result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when()
            .post("/admin/products")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(statusCode);
    }

    private static Stream<Arguments> createProductAndStatusCode() {
        return Stream.of(
            Arguments.arguments(new ProductRequestDto("연필", "이미지", 1000), HttpStatus.CREATED.value()),
            Arguments.arguments(new ProductRequestDto("", "이미지", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(new ProductRequestDto("", "", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(new ProductRequestDto("", null, 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(new ProductRequestDto(null, "이미지", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(new ProductRequestDto("연필", "이미지", -1), HttpStatus.BAD_REQUEST.value())
        );
    }

    @DisplayName("상품 수정 요청을 보냈을 때 성공하면 상태코드 204를 반환하고 잘못된 요청을 보내면 400을 반환한다.")
    @Rollback
    @MethodSource("updateProductAndStatusCode")
    @ParameterizedTest
    void update_product(Long id, ProductRequestDto productUpdateDto, int statusCode) {
        // given
        ProductRequestDto productRequestDto = new ProductRequestDto("지우개", "이미지", 2000);
        ExtractableResponse<Response> add = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .post("/admin/products")
            .then()
            .extract();

        // when
        ExtractableResponse<Response> result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .body(productUpdateDto)
            .when()
            .patch("/admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(statusCode);
    }

    private static Stream<Arguments> updateProductAndStatusCode() {
        return Stream.of(
            Arguments.arguments(1L, new ProductRequestDto("연필", "이미지", 1000), HttpStatus.NO_CONTENT.value()),
            Arguments.arguments(1L, new ProductRequestDto("", "이미지", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(1L, new ProductRequestDto("", "", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(1L, new ProductRequestDto("", null, 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(1L, new ProductRequestDto(null, "이미지", 1000), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(1L, new ProductRequestDto("연필", "이미지", -1), HttpStatus.BAD_REQUEST.value()),
            Arguments.arguments(100L, new ProductRequestDto("연필", "이미지", 1000), HttpStatus.BAD_REQUEST.value())
        );
    }

    @DisplayName("상품 삭제 요청을 보냈을 때 성공하면 상태코드 200을 반환하고 잘못된 요청을 보내면 400을 반환한다.")
    @Rollback
    @CsvSource(value = {"1,200", "100,400"})
    @ParameterizedTest
    void delete_product(Long id, int statusCode) {
        // given
        ProductRequestDto productRequestDto = new ProductRequestDto("지우개", "이미지", 2000);
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .post("/admin/products");

        // when
        ExtractableResponse<Response> result = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .when()
            .delete("/admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(statusCode);
    }
}