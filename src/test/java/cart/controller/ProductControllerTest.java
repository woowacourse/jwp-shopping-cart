package cart.controller;

import cart.dto.ProductRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("정상 요청을 테스트한다")
    void valid_create() {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto("test", "image.jpg", 100);

        //expect
        RestAssured.given().log().headers()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .headers("Location", "/admin");
    }

    @Test
    @DisplayName("RequestDto 에 null 이 포함되면 예외가 발생한다")
    void invalid_create_byBadRequestDto() {
        //given
        final ProductRequestDto requestDto = new ProductRequestDto(null, "image.jpg", 100);

        //expect
        RestAssured.given().log().headers()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestDto)
                .when().post("/products")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
