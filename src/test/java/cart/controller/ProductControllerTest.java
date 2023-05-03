package cart.controller;

import cart.controller.dto.ModifyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

@Sql("/test.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws URISyntaxException {
        RestAssured.port = port;
    }

    @DisplayName("POST /admin/product 요청 시")
    @Nested
    class postAdminProduct {

        @DisplayName("입력이 올바른 경우 Status Created 반환")
        @Test
        void shouldResponseStatusCreatedWhenRequestPostToAdminProduct() throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", 100, "domain.com");
            final String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson).log().all()
                    .when()
                    .post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_CREATED);
        }

        @DisplayName("이름이 공백인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldThrowExceptionWhenNameIsBlank(String inputName) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest(inputName, 100, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @DisplayName("가격이 0 미만인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "가격 입력 : {0}")
        @ValueSource(longs = {-1, -10000, -1000000})
        void shouldThrowExceptionWhenPriceIsUnderZero(long inputPrice) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", inputPrice, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @DisplayName("이미지의 URL이 공백인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldThrowExceptionWhenImageUrlIsBlank(String inputImageUrl) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", 100, inputImageUrl);
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .post("/product")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }
    }

    @DisplayName("PATCH /admin/product/{id} 요청 시")
    @Nested
    class putAdminProduct {

        @DisplayName("입력이 올바른 경우 Status OK 반환")
        @Test
        void shouldResponseStatusCreatedWhenRequestPutToAdminProductId() throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", 100, "domain.com");
            final String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .patch("/product/1")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_OK);
        }

        @DisplayName("상품이 존재하지 않을 경우 Status Not Found 반환")
        @Test
        void shouldResponseStatusBadRequestWhenRequestPutToNotExistId() throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", 100, "domain.com");
            final String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .patch(String.format("/product%d", Long.MAX_VALUE))
                    .then().log().all()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
        }

        @DisplayName("이름이 공백인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldThrowExceptionWhenNameIsBlank(String inputName) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest(inputName, 100, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .patch("/product/1")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @DisplayName("가격이 0 미만인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "가격 입력 : {0}")
        @ValueSource(longs = {-1, -10000, -1000000})
        void shouldThrowExceptionWhenPriceIsUnderZero(long inputPrice) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", inputPrice, "domain.super.com");
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .patch("/product/1")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

        @DisplayName("이미지의 URL이 공백인 경우 예외가 발생한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" "})
        @NullAndEmptySource
        void shouldThrowExceptionWhenImageUrlIsBlank(String inputImageUrl) throws JsonProcessingException {
            final ModifyRequest request = new ModifyRequest("사과", 100, inputImageUrl);
            String requestJson = objectMapper.writeValueAsString(request);
            given().log().all()
                    .contentType(ContentType.JSON)
                    .body(requestJson)
                    .when()
                    .patch("/product/1")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
        }

    }

    @DisplayName("DELETE /admin/product/{id} 요청 시")
    @Nested
    class DeleteAdminProduct {

        @DisplayName("Product가 있을 경우 Status OK 반환")
        @Test
        void shouldResponseStatusOkWhenRequestDeleteToAdminProductId() throws JsonProcessingException {
            given().log().all()
                    .when()
                    .delete("/product/1")
                    .then().log().all()
                    .statusCode(HttpStatus.SC_OK);
        }

        @DisplayName("Product가 없을 경우 Status Not Found 반환")
        @Test
        void shouldResponseStatusBadRequestWhenRequestProductNotExists() {
            given().log().all()
                    .when()
                    .delete(String.format("/product/%d", Long.MAX_VALUE))
                    .then().log().all()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
        }
    }
}
