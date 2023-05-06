package cart.web.controller.cart;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.domain.cart.usecase.FindAllProductsInCartUseCase;
import cart.domain.product.TestFixture;
import cart.domain.product.service.dto.ProductResponseDto;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindAllProductInCartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private FindAllProductsInCartUseCase findAllProductsInCartUseCase;

    @DisplayName("사용자 장바구니 내 상품 전체 조회 테스트")
    @Test
    void getProductsInCart() {
        when(findAllProductsInCartUseCase.findAllProductsInCart(any()))
                .thenReturn(List.of(ProductResponseDto.from(TestFixture.CHICKEN),
                        ProductResponseDto.from(TestFixture.PIZZA)));

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }
}
