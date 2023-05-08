package cart.web.controller.cart;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import cart.cart.usecase.DeleteOneProductInCartUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteProductInCartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private DeleteOneProductInCartUseCase deleteOneProductInCartUseCase;

    @DisplayName("사용자 장바구니 내 상품 삭제 테스트")
    @Test
    void deleteProductInCart() {
        doNothing().when(deleteOneProductInCartUseCase).deleteSingleProductInCart(any(), anyLong());

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().delete("/cart/{productId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
