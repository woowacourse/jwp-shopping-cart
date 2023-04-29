package cart.controller;

import cart.dto.ProductCreationRequest;
import cart.dto.ProductModificationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql("/testData.sql")
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    int port;

    @Nested
    @DisplayName("관리자 페이지를 조회하는 admin 메서드 테스")
    class SelectTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 조회가 되었다면 상태코드 200을 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .when().get("/admin")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    @DisplayName("상품을 등록하는 postProducts 메서드 테스트")
    class PostTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 등록이 되었다면 상태코드 201을 반환하는지 확인한다")
        @Test
        void successTest() {
            final ProductCreationRequest request = new ProductCreationRequest("pbo", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/admin/products")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        void throwExceptionWhenInvalidNameTest(final String nameInput) {
            final ProductCreationRequest request = new ProductCreationRequest(nameInput, "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10_000_000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/admin/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(ints = {0, 10_000_001})
        void throwExceptionWhenInvalidPriceTest(final int priceInput) {
            final ProductCreationRequest request = new ProductCreationRequest("pobi", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", priceInput);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/admin/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(strings = {" ", "", "image"})
        void throwExceptionWhenInvalidPriceTest(final String imageInput) {
            final ProductCreationRequest request = new ProductCreationRequest("pobi", imageInput, 10_000_000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().post("/admin/products")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("상품을 수정하는 putProducts 메서드 테스트")
    class PutTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 수정이 되었다면 상태코드 204를 반환하는지 확인한다")
        @Test
        void successTest() {
            final ProductModificationRequest request = new ProductModificationRequest(1L, "pbo", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10000000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/admin/products/" + request.getId())
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("잘못된 형식의 이름이 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        void throwExceptionWhenInvalidNameTest(final String nameInput) {
            final ProductModificationRequest request = new ProductModificationRequest(1L, nameInput, "ㅎㅎ", 10_000_000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/admin/products/" + request.getId())
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("잘못된 범위의 가격이 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(ints = {0, 10_000_001})
        void throwExceptionWhenInvalidPriceTest(final int priceInput) {
            final ProductModificationRequest request = new ProductModificationRequest(1L, "pobi", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", priceInput);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/admin/products/" + request.getId())
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("잘못된 형식의 이미지 경로가 들어오면 상태코드 400을 반환하는지 확인한다")
        @ParameterizedTest
        @ValueSource(strings = {" ", "", "image"})
        void throwExceptionWhenInvalidPriceTest(final String imageInput) {
            final ProductModificationRequest request = new ProductModificationRequest(1L, "pobi", imageInput, 10_000_000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/admin/products/" + request.getId())
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("존재하지 않는 id의 상품에 대한 수정 요청이 들어오면 상태코드 400을 반환하는지 확인한다")
        @Test
        void throwExceptionWhenInvalidIdTest() {
            final ProductModificationRequest request = new ProductModificationRequest(3L, "pobi", "https://cdn.polinews.co.kr/news/photo/201910/427334_3.jpg", 10_000_000);

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when().put("/admin/products/" + request.getId())
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("상품을 삭제하는 deleteProducts 테스트")
    class DeleteTest {

        @BeforeEach
        void setUp() {
            RestAssured.port = port;
        }

        @DisplayName("정상 삭제가 되었다면 상태코드 204를 반환하는지 확인한다")
        @Test
        void successTest() {
            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/admin/products/1")
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("존재하지 않는 id의 상품에 대한 삭제 요청이 들어오면 상태코드 400을 반환하는지 확인한다")
        @Test
        void throwExceptionWhenInvalidIdTest() {

            RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/admin/products/3")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

}
