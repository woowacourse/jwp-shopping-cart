package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.DeleteCartRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    private Long productId1;
    private Long productId2;
    private String accessToken;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = postWithBody("/customers", customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        accessToken = postWithBody("/customers/login", tokenRequest)
                .as(TokenResponse.class)
                .getAccessToken();
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        CartRequest cartRequest = new CartRequest(productId1, 5);
        ExtractableResponse<Response> response = postWithBodyByToken("/customers/carts", accessToken, cartRequest);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        CartRequest cartRequestProduct1 = new CartRequest(productId1, 5);
        CartRequest cartRequestProduct2 = new CartRequest(productId2, 3);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct1);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct2);

        ExtractableResponse<Response> response = getByToken("/customers/carts", accessToken);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<CartResponse> resultCartList = new ArrayList<>(response.jsonPath().getList("carts", CartResponse.class));
        assertThat(resultCartList.stream().map(CartResponse::getQuantity)).containsExactly(5, 3);
    }


    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        CartRequest cartRequestProduct1 = new CartRequest(productId1, 5);
        CartRequest cartRequestProduct2 = new CartRequest(productId2, 3);
        DeleteCartRequest deleteCartRequest = new DeleteCartRequest(List.of(productId1));
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct1);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct2);

        ExtractableResponse<Response> response = deleteWithBodyByToken("/customers/carts", accessToken,
                deleteCartRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("장바구니 수량 수정")
    @Test
    void updateCartItemQuantity() {
        CartRequest cartRequestProduct1 = new CartRequest(productId1, 5);
        CartRequest cartRequestProduct2 = new CartRequest(productId2, 3);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct1);
        postWithBodyByToken("/customers/carts", accessToken, cartRequestProduct2);

        CartRequest cartRequest = new CartRequest(1L, 11);
        ExtractableResponse<Response> response = patchByTokenWithBody("/customers/carts", accessToken, cartRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", productId);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/api/customers/{customerName}/carts", userName)
                .then().log().all()
                .extract();
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }
}
