package cart.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.product.ProductRequest;
import cart.dto.product.ProductResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminProductAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("상품을 등록할 때 성공하면 상태코드 201을 반환한다.")
    @Rollback
    @Test
    void create_product_success() {
        // givn
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ProductRequest productAddRequest = new ProductRequest("연필", "이미지", 1000);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productAddRequest)
            .when()
            .post("admin/products")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("상품을 등록할 때 잘못된 요청이 오면 상태코드 400을 반환한다.")
    @Rollback
    @MethodSource("createWrongProduct")
    @ParameterizedTest(name = "{displayName}")
    void create_product_fail(ProductRequest productRequest) {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .when()
            .post("admin/products")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<Arguments> createWrongProduct() {
        return Stream.of(
            Arguments.arguments(new ProductRequest("", "이미지", 1000)),
            Arguments.arguments(new ProductRequest("", "", 1000)),
            Arguments.arguments(new ProductRequest("", null, 1000)),
            Arguments.arguments(new ProductRequest(null, "이미지", 1000)),
            Arguments.arguments(new ProductRequest("연필", "이미지", -1))
        );
    }

    @DisplayName("권한 없는 사용자가 상품을 추가하면 상태코드 401을 반환한다.")
    @Rollback
    @Test
    void create_product_fail_no_authority() {
        // givn
        String userEmail = "ako@wooteco.com";
        String userPassword = "ako";
        ProductRequest productAddRequest = new ProductRequest("연필", "이미지", 1000);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(userEmail, userPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productAddRequest)
            .when()
            .post("admin/products")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Order(1)
    @DisplayName("사용자가 상품을 조회하면 상태코드 200을 반환한다.")
    @Rollback
    @Test
    void read_product_success() {
        // givn
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        String responseBody = "[{\"id\":1,\"name\":\"연필\",\"imageUrl\":\"이미지\",\"price\":1000},"
            + "{\"id\":2,\"name\":\"지우개\",\"imageUrl\":\"이미지\",\"price\":2000},"
            + "{\"id\":3,\"name\":\"볼펜\",\"imageUrl\":\"이미지\",\"price\":3000}]";

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("admin/products")
            .then()
            .extract();


        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.response().body().as(List.class).size()).isEqualTo(3);
        assertThat(result.response().body().asString()).isEqualTo(responseBody);
    }

    @DisplayName("권한 없는 사용자가 상품을 조회하면 상태코드 401을 반환한다.")
    @Rollback
    @Test
    void read_product_fail_no_authority() {
        // givn
        String userEmail = "ako@wooteco.com";
        String userPassword = "ako";

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(userEmail, userPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("admin/products")
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("상품 수정 요청을 보냈을 때 성공하면 상태코드 204를 보내준다.")
    @Rollback
    @Test
    void update_product_success() {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);
        ProductRequest productUpdateDto = new ProductRequest("지우개", "이미지 url", 2000);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .body(productUpdateDto)
            .when()
            .put("admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("없는 상품에 대한 수정 요청을 보냈을 때는 상태코드 404를 보내준다.")
    @Rollback
    @Test
    void update_not_exist_product() {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);
        ProductRequest productUpdateDto = new ProductRequest("지우개", "이미지 url", 2000);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id + 1)
            .body(productUpdateDto)
            .when()
            .put("admin/products/" + id + 1)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("권한 없는 사용자가 상품 수정 요청을 보냈을 때는 상태코드 401를 보내준다.")
    @Rollback
    @Test
    void update_product_fail_no_authority() {
        // given
        String userEmail = "ako@wooteco.com";
        String userPassword = "ako";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);
        ProductRequest productUpdateDto = new ProductRequest("지우개", "이미지 url", 2000);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(userEmail, userPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .body(productUpdateDto)
            .when()
            .put("admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("상품 수정 요청을 보냈을 때 잘못된 상품을 요청 보내면 상태코드 400을 반환한다.")
    @Rollback
    @MethodSource("updateWrongProduct")
    @ParameterizedTest(name = "{displayName}")
    void update_product_fail(ProductRequest productUpdateDto) {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .body(productUpdateDto)
            .when()
            .put("admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static Stream<Arguments> updateWrongProduct() {
        return Stream.of(
            Arguments.arguments(new ProductRequest("", "이미지", 1000)),
            Arguments.arguments(new ProductRequest("", "", 1000)),
            Arguments.arguments(new ProductRequest("", null, 1000)),
            Arguments.arguments(new ProductRequest(null, "이미지", 1000)),
            Arguments.arguments(new ProductRequest("연필", "이미지", -1))
        );
    }

    @DisplayName("상품 삭제 요청을 보냈을 때 성공하면 상태코드 200과 삭제된 상품의 아이디를 반환한다.")
    @Rollback
    @Test
    void delete_product_success() {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id)
            .when()
            .delete("admin/products/" + id)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.body().as(Long.class)).isEqualTo(id);
    }

    @DisplayName("없는 상품에 대해서 삭제 요청을 보내면 상태코드 404를 반환한다.")
    @Rollback
    @Test
    void delete_product_fail() {
        // given
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id + 1)
            .when()
            .delete("admin/products/" + id + 1)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("권한 없는 사용자가 상품 삭제 요청을 보내면 상태코드 401를 반환한다.")
    @Rollback
    @Test
    void delete_product_fail_no_authority() {
        // given
        String userEmail = "ako@wooteco.com";
        String userPassword = "ako";
        ExtractableResponse<Response> addResult = addProduct();
        Long id = Long.valueOf(addResult.header("Location").split("/")[1]);

        // when
        ExtractableResponse<Response> result = given()
            .auth().preemptive().basic(userEmail, userPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("id", id + 1)
            .when()
            .delete("admin/products/" + id + 1)
            .then()
            .extract();

        // then
        assertThat(result.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> addProduct() {
        String adminEmail = "admin@wooteco.com";
        String adminPassword = "admin";

        ProductRequest productRequest = new ProductRequest("지우개", "이미지", 2000);
        return given()
            .auth().preemptive().basic(adminEmail, adminPassword)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequest)
            .post("admin/products")
            .then()
            .extract();
    }
}
