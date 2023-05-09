package cart.controller;

import cart.dto.CartRequestDto;
import cart.dto.CartResponseDto;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
    @DisplayName("/carts (Post) 요청을 보내 장바구니에 상품을 저장한다.")
    void addProductToCart() {
        CartRequestDto cartRequestDto = new CartRequestDto(2L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("/carts (Post) 요청을 보내 상품을 저장하는 과정에서 잘못된 ID 를 보내 에러 발생")
    void addProductToCartFail() {
        CartRequestDto cartRequestDto = new CartRequestDto(0L);

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartRequestDto)
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("/carts (Get) 요청을 보내 장바구니에 있는 상품을 조회한다.")
    void cartsList() {
        List<CartResponseDto> cartResponseDtos = RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(new TypeRef<>() {});

        assertThat(cartResponseDtos)
                .anyMatch(cartResponseDto -> cartResponseDto.getEmail().equals(EMAIL))
                .anyMatch(cartResponseDto -> cartResponseDto.getProductResponseDto().getName().equals("샐러드"));
    }

    @Test
    @DisplayName("/carts/{id} (Delete) 요청을 보내 장바구니에 상품을 삭제한다.")
    void removeProductToCart() {
        RestAssured.given().log().all()
                .auth().preemptive().basic(EMAIL, PASSWORD)
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
