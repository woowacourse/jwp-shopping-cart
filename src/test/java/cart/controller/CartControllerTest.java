package cart.controller;

import cart.dto.CartResponseDto;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private static final String EMAIL = "kpeel5839@a.com";
    private static final String PASSWORD = "password1!";
    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("/carts/{id} (Post) 요청을 보내 장바구니에 상품을 저장한다.")
    void addProductToCart() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

}
