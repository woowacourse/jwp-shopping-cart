package woowacourse.shoppingcart.acceptance;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenResponseDto;
import woowacourse.shoppingcart.dto.request.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.request.ProductRequestDto;
import woowacourse.shoppingcart.dto.request.SignUpDto;
import woowacourse.shoppingcart.dto.request.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponseDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.Fixture.*;

@DisplayName("장바구니 관련 기능")
public class CartItemAcceptanceTest extends AcceptanceTest {

    private String accessToken;
    private Long customerId;
    private Header authorizationHeader;
    private Long productId1;
    private Long productId2;

    @BeforeEach
    void setCustomer() {
        final SignUpDto signUpDto = new SignUpDto(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME);
        final ExtractableResponse<Response> customerResponse = createCustomer(signUpDto);
        final String[] locations = customerResponse.header("Location").split("/");
        customerId = Long.valueOf(locations[locations.length - 1]);
        final ExtractableResponse<Response> loginResponse = loginCustomer(TEST_EMAIL, TEST_PASSWORD);
        final TokenResponseDto tokenResponseDto = loginResponse.body().as(TokenResponseDto.class);
        accessToken = tokenResponseDto.getAccessToken();
        authorizationHeader = new Header(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
    }

    @BeforeEach
    void setProducts() {
        final ProductRequestDto productRequestDto1 = new ProductRequestDto("product1", 10000, null, 10);
        final ProductRequestDto productRequestDto2 = new ProductRequestDto("product2", 11000, null, 10);

        final ExtractableResponse<Response> productResponse1 = post("/api/products", EMPTY_HEADER, productRequestDto1);
        final String[] locations1 = productResponse1.header("Location").split("/");
        productId1 = Long.valueOf(locations1[locations1.length - 1]);
        final ExtractableResponse<Response> productResponse2 = post("/api/products", EMPTY_HEADER, productRequestDto2);
        final String[] locations2 = productResponse2.header("Location").split("/");
        productId2 = Long.valueOf(locations2[locations2.length - 1]);
    }

    @Test
    @DisplayName("장바구니에 담긴 물건들을 불러온다.")
    void getCartItems() {

        final AddCartItemRequestDto addCartItemRequestDto1 = new AddCartItemRequestDto(productId1, 1);
        final AddCartItemRequestDto addCartItemRequestDto2 = new AddCartItemRequestDto(productId2, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto1);
        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto2);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts", authorizationHeader);

        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(2);
        assertThat(cartItemResponseDtos.get(0).getProductId()).isEqualTo(productId1);
        assertThat(cartItemResponseDtos.get(1).getProductId()).isEqualTo(productId2);
    }

    @Test
    @DisplayName("장바구니에 물건을 담는다.")
    void addCartItem() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts", authorizationHeader);
        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(1);
        assertThat(cartItemResponseDtos.get(0).getProductId()).isEqualTo(productId1);
    }

    @Test
    @DisplayName("장바구니에 물건을 담을때 이미 담겨있는 품목이면 예외가 발생한다.")
    void addCartItem_DuplicateProductException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);
        final ExtractableResponse<Response> response = post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("장바구니에 물건을 담을때 재고가 수량보다 적을경우 예외가 발생한다.")
    void addCartItem_DupalicateProductException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 11);

        final ExtractableResponse<Response> response = post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("장바구니에 담긴 물건의 수량을 변경한다.")
    void updateCartItems() {
        final AddCartItemRequestDto addCartItemRequestDto1 = new AddCartItemRequestDto(productId1, 1);
        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto1);

        final UpdateCartItemCountItemRequest updateCartItemCountItemRequest = new UpdateCartItemCountItemRequest(2);
        patch("/api/customers/" + customerId + "/carts?productId=" + productId1, authorizationHeader, updateCartItemCountItemRequest);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts", authorizationHeader);
        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(1);
        assertThat(cartItemResponseDtos.get(0).getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에 담긴 물건을 삭제한다.")
    void deleteCartItem() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        delete("/api/customers/" + customerId + "/carts?productId=" + productId1, authorizationHeader);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts", authorizationHeader);
        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(0);
    }
}
