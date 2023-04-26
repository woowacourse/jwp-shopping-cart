package cart.exception;

import static org.hamcrest.core.Is.is;

import cart.dto.request.ProductRequestDto;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ExceptionAdviceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("유효하지 않은 정보로 생성 요청이 들어올시 400 상태코드와 오류메시지를 담아서 응답한다.")
    void handleException() {
        //given
        final ProductRequestDto productRequestDto = new ProductRequestDto(
            null,
            "imageUrl",
            1000,
            "description",
            List.of(1L, 2L)
        );

        //when
        //then
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(productRequestDto)
            .when().post("/products")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", is("상품명이 존재하지 않습니다."));
    }
}
