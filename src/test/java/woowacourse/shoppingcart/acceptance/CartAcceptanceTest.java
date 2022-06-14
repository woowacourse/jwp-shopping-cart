package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.Fixture.BEARER;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.SignInResponseDto;
import woowacourse.shoppingcart.dto.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.CartItemResponseDto;
import woowacourse.shoppingcart.dto.ProductRequestDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCartItemCountItemRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

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
        final SignInResponseDto signInResponseDto = loginResponse.body().as(SignInResponseDto.class);
        accessToken = signInResponseDto.getAccessToken();
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

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts",
                authorizationHeader);

        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니에 물건을 담는다.")
    void addCartItem() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts",
                authorizationHeader);
        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("없는 productId로 장바구니에 물건을 담는다.")
    void addCartItem_notFoundProductId() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(1000L, 1);

        final ExtractableResponse<Response> response
                = post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("없는 productId로 장바구니에 물건을 담는다.")
    void addCartItem_duplicateProductId() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);
        final ExtractableResponse<Response> response
                = post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("재고보다 많은 수량의 물건을 장바구니에 담는다.")
    void addCartItem_overCount() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 11);

        final ExtractableResponse<Response> response
                = post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("장바구니에 담긴 물건의 수량을 변경한다.")
    void updateCartItems() {
        final AddCartItemRequestDto addCartItemRequestDto1 = new AddCartItemRequestDto(productId1, 1);
        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto1);

        final UpdateCartItemCountItemRequest updateCartItemCountItemRequest = new UpdateCartItemCountItemRequest(2);
        patch("/api/customers/" + customerId + "/carts?productId=" + productId1, authorizationHeader,
                updateCartItemCountItemRequest);

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts",
                authorizationHeader);
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

        final ExtractableResponse<Response> cartItemsResponse = get("/api/customers/" + customerId + "/carts",
                authorizationHeader);
        final List<CartItemResponseDto> cartItemResponseDtos
                = cartItemsResponse.body().jsonPath().getList(".", CartItemResponseDto.class);

        assertThat(cartItemResponseDtos.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("본인이 아닌 id로 장바구니 물건 삭제를 요청한다.")
    void deleteCartItem_forbidden() {
        // given
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId1, 1);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto);

        final String test2Email = "test@test.co.kr";
        final String test2Password = TEST_PASSWORD + "2";
        final String test2Username = TEST_USERNAME + "2";
        final SignUpDto signUpDto = new SignUpDto(test2Email, test2Password, test2Username);
        createCustomer(signUpDto);
        final ExtractableResponse<Response> loginResponse = loginCustomer(test2Email, test2Password);
        final SignInResponseDto signInResponseDto = loginResponse.body().as(SignInResponseDto.class);
        final String accessToken2 = signInResponseDto.getAccessToken();

        // when
        final ExtractableResponse<Response> response = delete(
                "/api/customers/" + customerId + "/carts?productId=" + productId1,
                new Header(HttpHeaders.AUTHORIZATION, BEARER + accessToken2)
        );

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
