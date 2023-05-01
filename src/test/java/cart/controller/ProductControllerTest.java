package cart.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/schema.sql"})
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 상품_목록_조회() {
        final String htmlBody = RestAssured.when()
                .get("/products")
                .then()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        assertThat(htmlBody.contains("<title>상품목록</title>")).isTrue();
    }
}
