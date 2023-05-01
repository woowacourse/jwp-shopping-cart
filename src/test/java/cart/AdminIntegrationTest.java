package cart;

import static org.hamcrest.core.Is.is;

import cart.domain.Product;
import cart.dto.request.ProductRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.persistence.H2ProductDao;
import cart.persistence.ProductDao;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminIntegrationTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/admin으로 get 요청을 보내면 ok 상태코드를 반환한다")
    void adminTest() {
        RestAssured.given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/admin")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
