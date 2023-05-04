package cart.web.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import cart.domain.cart.service.CartService;
import cart.domain.product.TestFixture;
import cart.domain.product.service.dto.ProductDto;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.controller.dto.request.ProductInCartAdditionRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class CartRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private CartService cartService;

    @DisplayName("사용자 장바구니에 상품을 등록 할 수 있다.")
    @Test
    void addProductInCart() throws JsonProcessingException {
        doNothing().when(cartService).addProductInCart(any(), anyLong());
        final ProductInCartAdditionRequest request = new ProductInCartAdditionRequest(1L);

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMapper().writeValueAsBytes(request))
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("사용자 장바구니 내 상품 조회 테스트")
    @Test
    void getProductsInCart() {
        when(cartService.findAllProductsInCart(any()))
                .thenReturn(List.of(ProductDto.from(TestFixture.CHICKEN), ProductDto.from(TestFixture.PIZZA)));

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2));
    }

    @DisplayName("사용자 장바구니 내 상품 삭제 테스트")
    @Test
    void deleteProductInCart() {
        doNothing().when(cartService).deleteProductInCart(any(), anyLong());

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .when().delete("/cart/{productId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
