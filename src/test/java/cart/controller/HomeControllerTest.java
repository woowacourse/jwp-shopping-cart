package cart.controller;

import cart.dao.ProductEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class HomeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("홈 컨트롤러의 맵핑 기능이 정상적으로 작동한다")
    void homeController() {

        ProductEntity productEntity = new ProductEntity(1, "pizza", "https://www.hmj2k.com/data/photos/20210936/art_16311398425635_31fd17.jpg", 1000);

        RestAssured.given()
                .log()
                .all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productEntity)
                .when()
                .get("/")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
    }
}