package cart.controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("NonAsciiCharacters")
class CartControllerTest {

    @BeforeEach
    void init(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 유저별로_장바구니를_조회한다() {
        // when
        final ExtractableResponse<Response> response = givenWithAuth()
                .when()
                .get("/carts/users")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        // then
        final JsonPath result = response.jsonPath();
        assertThat(result.getList("cartResponses.name")).containsExactly("치킨", "샐러드");
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 장바구니를_추가한다() {
        // when
        final ExtractableResponse<Response> response = givenWithAuth()
                .when()
                .post("/carts/users/products/{productId}", 3L)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        // then
        assertThat(response.header("Location")).isEqualTo("/carts/users/1/products/3");
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 장바구니를_삭제한다() {
        // when, then
        givenWithAuth()
                .when()
                .delete("/carts/users/products/{productId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();

    }

    private RequestSpecification givenWithAuth() {
        return RestAssured.given().log().all()
                .auth().preemptive().basic("test@test.com", "test");
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 잘못된_인증정보로_API를_호출하면_권한_예외가_발생한다() {
        final String basicValue = "test@test.com:invalid";
        final String encodedBasicValue = Base64.encodeBase64String(basicValue.getBytes());

        RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedBasicValue)
                .when()
                .get("/carts/users")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract();
    }
}
