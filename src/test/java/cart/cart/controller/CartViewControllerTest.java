package cart.cart.controller;

import cart.cart.dto.CartResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartViewControllerTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setPort() {
        RestAssured.port = port;
    }

    @Test
    void 장바구니_페이지_조회() {
        given().when()
                .log().all()
                .get("cart")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML);
    }

    @Test
    void 개별_장바구니_조회() {
        List<CartResponse> cartResponses = given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath().getList(".", CartResponse.class);

        CartResponse firstCartResponse = cartResponses.get(0);
        CartResponse secondCartResponse = cartResponses.get(1);

        assertThat(firstCartResponse.getId()).isEqualTo(1L);
        assertThat(secondCartResponse.getId()).isEqualTo(2L);
    }
}