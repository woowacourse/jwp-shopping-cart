package cart.controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("NonAsciiCharacters")
class CartControllerTest {

    private int port;

    @BeforeEach
    void init(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 유저별로_장바구니를_조회한다() {
        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().preemptive().basic("test@test.com", "test")
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
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().preemptive().basic("test@test.com", "test")
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
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().preemptive().basic("test@test.com", "test")
                .when()
                .delete("/carts/users/products/{productId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }
}
