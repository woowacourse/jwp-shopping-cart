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
import static org.junit.jupiter.api.Assertions.assertAll;

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
        assertAll(
                () -> assertThat(result.getList("cartResponses.name")).containsExactly("치킨", "샐러드"),
                () -> assertThat(result.getList("cartResponses.count")).containsExactly(3, 5)
        );
    }
}
