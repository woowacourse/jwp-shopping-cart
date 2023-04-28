package cart.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import cart.dto.ProductPostRequest;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AdminApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class CRUDSuccessTest {

        @Test
        void Product_POST_API_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "https://woowacourse.github.io/");

            final ProductEntity savedEntity = productDao.findByName("modi");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                softAssertions.assertThat(response.header("Location")).contains("/admin/product/");
                softAssertions.assertThat(savedEntity.getName()).isEqualTo("modi");
                softAssertions.assertThat(savedEntity.getPrice()).isEqualTo(10000);
                softAssertions.assertThat(savedEntity.getImageUrl()).isEqualTo("https://woowacourse.github.io/");
            });
        }

        @Test
        void Product_GET_API_테스트() {
            saveProduct("modi", 10000, "https://woowacourse.github.io/");

            given()
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
        }

        @Test
        void Product_UPDATE_API_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "https://woowacourse.github.io/");
            final String[] locations = response.header("Location").split("/");
            final String id = locations[locations.length - 1];

            final ProductPostRequest productPostRequest = new ProductPostRequest("modi", 15000, "https://changed.com/");
            given()
                .body(productPostRequest)
                .when().put("/admin/product/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

            final ProductEntity changedEntity = productDao.findByName("modi");
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                softAssertions.assertThat(response.header("Location")).contains("/admin/product/" + changedEntity.getId());
                softAssertions.assertThat(changedEntity.getName()).isEqualTo("modi");
                softAssertions.assertThat(changedEntity.getPrice()).isEqualTo(15000);
                softAssertions.assertThat(changedEntity.getImageUrl()).isEqualTo("https://changed.com/");
            });
        }

        @Test
        void Product_DELETE_API_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "https://woowacourse.github.io/");
            final String[] locations = response.header("Location").split("/");
            final String id = locations[locations.length - 1];

            given()
                .when().delete("/admin/product/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

            assertThrows(EmptyResultDataAccessException.class, () -> productDao.findByName("modi"));
        }
    }

    @Nested
    class CRUDRequestExceptionTest {

        @Test
        void Product_POST_상품_명_공백_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("", 10000, "https://woowacourse.github.io/");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString()).contains("상품 명을 입력해주세요");
            });
        }

        @Test
        void Product_POST_0_미만_가격_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", -1, "https://woowacourse.github.io/");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString()).contains("유효한 가격을 입력해주세요");
            });
        }

        @Test
        void Product_POST_URL_공백_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString()).contains("유효한 이미지 URL을 입력해주세요");
            });
        }

        @Test
        void Product_POST_URL_길이_초과_예외_테스트() {
            final String url = "a".repeat(513);
            final ExtractableResponse<Response> response = saveProduct("modi", 10, url);

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString()).contains("유효한 이미지 URL을 입력해주세요");
            });
        }

        @Test
        void Product_POST_상품_명_공백_및_URL_공백_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("", 10000, "");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString())
                    .contains("상품 명을 입력해주세요")
                    .contains("유효한 이미지 URL을 입력해주세요");
            });
        }

        @Test
        void Product_POST_상품_명_공백_및_0_URL_공백_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("", -10, "");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString())
                    .contains("상품 명을 입력해주세요")
                    .contains("유효한 가격을 입력해주세요")
                    .contains("유효한 이미지 URL을 입력해주세요");
            });
        }

        @Test
        void Product_POST_10원_단위_아닌_가격_예외_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 9, "https://woowacourse.github.io/");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                softAssertions.assertThat(response.body().asString()).contains("금액은 10원 단위여야 합니다.");
            });
        }

    }

    private ExtractableResponse<Response> saveProduct(final String name, final int price, final String imageUrl) {
        final ProductPostRequest request = new ProductPostRequest(name, price, imageUrl);

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
