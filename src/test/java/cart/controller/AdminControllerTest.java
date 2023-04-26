package cart.controller;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void Product_POST_API_테스트() {
        final ExtractableResponse<Response> response = saveProduct("modi", 10000, "");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softAssertions.assertThat(response.header("Location")).contains("/admin/product/");
        });
    }

    @Test
    void Product_GET_API_테스트() {
        saveProduct("modi", 10000, "");

        given()
            .when()
            .get("/admin")
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Product_UPDATE_API_테스트() {
        final ExtractableResponse<Response> response = saveProduct("modi", 10000, "");
        final String[] locations = response.header("Location").split("/");
        final String id = locations[locations.length - 1];

        final ProductRequest productRequest = new ProductRequest("modi", 15000, "");
        given()
            .body(productRequest)
            .when().put("/admin/product/" + id)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void Product_DELETE_API_테스트() {
        final ExtractableResponse<Response> response = saveProduct("modi", 10000, "");
        final String[] locations = response.header("Location").split("/");
        final String id = locations[locations.length - 1];

        given()
            .when().delete("/admin/product/" + id)
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    private ExtractableResponse<Response> saveProduct(final String name, final int price, final String imageUrl) {
        final ProductRequest request = new ProductRequest(name, price, imageUrl);

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
