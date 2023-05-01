package cart;

import static org.assertj.core.api.Assertions.*;
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

import cart.domain.admin.persistence.dao.ProductDao;
import cart.domain.admin.persistence.entity.ProductEntity;
import cart.web.admin.dto.PostProductRequest;
import cart.web.admin.dto.PutProductRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AdminApiEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private ExtractableResponse<Response> saveProduct(final String name, final int price, final String imageUrl) {
        final PostProductRequest request = new PostProductRequest(name, price, imageUrl);

        return given()
            .body(request)
            .when()
            .post("/admin/products")
            .then()
            .extract();
    }

    private RequestSpecification given() {
        return RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Nested
    class CRUDSuccessTest {

        @Test
        void Product_POST_API_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "https://woowacourse.github.io/");

            final ProductEntity savedEntity = productDao.findByName("modi");

            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                softAssertions.assertThat(response.header("Location")).contains("/admin/products/");
                softAssertions.assertThat(savedEntity.getName()).isEqualTo("modi");
                softAssertions.assertThat(savedEntity.getPrice()).isEqualTo(10000);
                softAssertions.assertThat(savedEntity.getImageUrl()).isEqualTo("https://woowacourse.github.io/");
            });
        }

        @Test
        void Product_GET_API_테스트() {
            final ProductEntity productEntity = new ProductEntity("modi", 10000, "https://woowacourse.github.io/");
            productDao.save(productEntity);

            given()
                .when()
                .get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
        }

        @Test
        void Product_UPDATE_API_테스트() {
            final ExtractableResponse<Response> response = saveProduct("modi", 10000, "https://woowacourse.github.io/");
            final String[] locations = response.header("Location").split("/");
            final String id = locations[locations.length - 1];

            final PostProductRequest postProductRequest = new PostProductRequest("modi", 15000, "https://changed.com/");
            given()
                .body(postProductRequest)
                .when().put("/admin/products/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());

            final ProductEntity changedEntity = productDao.findByName("modi");
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
                softAssertions.assertThat(response.header("Location")).contains("/admin/products/" + changedEntity.getId());
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
                .when().delete("/admin/products/" + id)
                .then()
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

        @Test
        void Product_PUT_없는_ID_예외_테스트() {
            final ProductEntity request = new ProductEntity("modi", 10000, "https://woowacourse.github.io/");
            productDao.save(request);

            final Long wrongId = 0L;
            final PutProductRequest putRequest = new PutProductRequest("modi", 7770, "https://woowacourse.github.io/");

            final ExtractableResponse<Response> response = given()
                .body(putRequest)
                .when()
                .put("/admin/products/" + wrongId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract();

            assertThat(response.body().asString()).contains("변경된 정보가 없습니다.");
        }

        @Test
        void Product_DELETE_없는_ID_예외_테스트() {
            final Long wrongId = 0L;

            final ExtractableResponse<Response> response = given()
                .when()
                .delete("/admin/products/" + wrongId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract();

            assertThat(response.body().asString()).contains("변경된 정보가 없습니다.");
        }
    }
}
