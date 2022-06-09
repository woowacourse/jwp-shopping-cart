package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static woowacourse.auth.acceptance.AuthAcceptanceTest.*;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CartItemDeletionRequest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ThumbnailImageDto;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private final static String URL = "/api/mycarts";
    private Header header;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ProductRequest productRequest1 = new ProductRequest("치킨", 10_000, 10,
            new ThumbnailImageDto("http://example.com/chicken.jpg", "이미지입니다."));
        ProductRequest productRequest2 = new ProductRequest("맥주", 20_000, 10,
            new ThumbnailImageDto("http://example.com/beer.jpg", "이미지입니다."));

        header = getTokenHeader();
        productId1 = getAddedProductId(productRequest1);
        productId2 = getAddedProductId(productRequest2);
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, 10);

        // when
        ExtractableResponse<Response> responseAddedCartItem = AcceptanceFixture.post(cartItemRequest, URL, header);
        CartItemResponse cartItemResponse = extractCartItem(responseAddedCartItem);

        // then
        assertThat(responseAddedCartItem.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(responseAddedCartItem.header("Location")).isEqualTo(URL + "/" + cartItemResponse.getId());
        assertThat(cartItemResponse)
            .extracting("productId", "price", "name", "quantity")
            .containsExactly(cartItemRequest.getProductId(), 10_000, "치킨",
                cartItemRequest.getQuantity());
    }

    @DisplayName("이미 장바구니에 있는 제품을 추가할 경우 5001번 에러를 반환한다.")
    @Test
    void addCartAlreadyExistProduct() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, 10);
        AcceptanceFixture.post(cartItemRequest, URL, header);

        // when
        ExtractableResponse<Response> response = AcceptanceFixture.post(cartItemRequest, URL, header);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(extractErrorCode(response)).isEqualTo(5001);
    }

    @DisplayName("장바구니에 담으려는 수량이 음수이면 5002번 에러를 반환한다.")
    @Test
    void addCartInvalidQuantity() {
        // given
        CartItemRequest cartItemRequest = new CartItemRequest(productId1, -1);

        // when
        ExtractableResponse<Response> response = AcceptanceFixture.post(cartItemRequest, URL, header);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(extractErrorCode(response)).isEqualTo(5002);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        // given
        AcceptanceFixture.post(new CartItemRequest(productId1, 10), URL, header);
        AcceptanceFixture.post(new CartItemRequest(productId2, 10), URL, header);

        // when
        ExtractableResponse<Response> responseAboutGetItems = AcceptanceFixture.get(URL, header);
        List<CartItemResponse> cartItemResponses = responseAboutGetItems.jsonPath()
            .getList(".", CartItemResponse.class);
        List<Long> productIds = cartItemResponses.stream()
            .map(CartItemResponse::getProductId)
            .collect(Collectors.toList());

        // then
        assertThat(responseAboutGetItems.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(productIds).contains(productId1, productId2);
    }

    @DisplayName("장바구니 단건 조회")
    @Test
    void getCartItem() {
        // given
        ExtractableResponse<Response> responseAboutCreatedCartItem1 = AcceptanceFixture.post(
            new CartItemRequest(productId1, 10), URL,
            header);
        AcceptanceFixture.post(new CartItemRequest(productId2, 10), URL, header);
        CartItemResponse createdCartItemResponse = extractCartItem(responseAboutCreatedCartItem1);

        // when
        ExtractableResponse<Response> responseAboutGetItem = AcceptanceFixture.get(
            URL + "/" + createdCartItemResponse.getId(), header);
        CartItemResponse cartItemResponse = extractCartItem(responseAboutGetItem);

        // then
        assertThat(cartItemResponse)
            .extracting("id", "productId", "price", "quantity")
            .containsExactly(createdCartItemResponse.getId(), createdCartItemResponse.getProductId(),
                createdCartItemResponse.getPrice(), createdCartItemResponse.getQuantity());
    }

    @DisplayName("장바구니 갯수 업데이트")
    @Test
    void update() {
        // given
        ExtractableResponse<Response> responseAboutCreatedCartItem1 = AcceptanceFixture.post(
            new CartItemRequest(productId1, 10), URL,
            header);
        AcceptanceFixture.post(new CartItemRequest(productId2, 10), URL, header);
        CartItemResponse createdCartItemResponse = extractCartItem(responseAboutCreatedCartItem1);

        // when
        AcceptanceFixture.patch(new UpdateCartItemRequest(createdCartItemResponse.getId(), 15), URL, header);
        ExtractableResponse<Response> responseAboutGetItem = AcceptanceFixture.get(
            URL + "/" + createdCartItemResponse.getId(), header);
        CartItemResponse cartItemResponse = extractCartItem(responseAboutGetItem);

        // then
        assertThat(cartItemResponse.getQuantity()).isEqualTo(15);
    }

    @DisplayName("장바구니 갯수 업데이트 시 수량이 음수이면 5002번 에러를 반환한다.")
    @Test
    void updateInvalidQuantity() {
        // given
        ExtractableResponse<Response> responseAboutCreatedCartItem1 = AcceptanceFixture.post(
            new CartItemRequest(productId1, 10), URL,
            header);
        AcceptanceFixture.post(new CartItemRequest(productId2, 10), URL, header);
        CartItemResponse createdCartItemResponse = extractCartItem(responseAboutCreatedCartItem1);

        // when
        ExtractableResponse<Response> patchResponse = AcceptanceFixture.patch(
            new UpdateCartItemRequest(createdCartItemResponse.getId(), -1), URL, header);

        // then
        assertThat(patchResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(extractErrorCode(patchResponse)).isEqualTo(5002);
    }

    @DisplayName("장바구니에서 선택적으로 삭제")
    @Test
    void delete() {
        // given
        ExtractableResponse<Response> responseAboutCreatedCartItem1 = AcceptanceFixture.post(
            new CartItemRequest(productId1, 10), URL,
            header);
        ExtractableResponse<Response> responseAboutCreatedCartItem2 = AcceptanceFixture.post(
            new CartItemRequest(productId2, 10), URL,
            header);
        CartItemResponse createdCartItemResponse1 = extractCartItem(responseAboutCreatedCartItem1);
        extractCartItem(responseAboutCreatedCartItem2);

        // when
        CartItemDeletionRequest cartItemDeletionRequest = new CartItemDeletionRequest(
            List.of(createdCartItemResponse1.getId()));
        AcceptanceFixture.delete(cartItemDeletionRequest, URL, header);

        // then
        ExtractableResponse<Response> response = AcceptanceFixture.get(URL, header);
        List<CartItemResponse> cartItemResponses = response.jsonPath().getList(".", CartItemResponse.class);
        assertThat(cartItemResponses.size()).isEqualTo(1);
    }

    private Header getTokenHeader() {
        final CustomerRequest customerRequest =
            new CustomerRequest("email@email.com", "password1!", "dwoo");
        AcceptanceFixture.post(customerRequest, "/api/customers");

        final TokenRequest tokenRequest = new TokenRequest(customerRequest.getEmail(), customerRequest.getPassword());
        final ExtractableResponse<Response> loginResponse = AcceptanceFixture.post(tokenRequest, "/api/auth/login");

        final String accessToken = extractAccessToken(loginResponse);

        return new Header("Authorization", BEARER + accessToken);
    }

    public static CartItemResponse extractCartItem(ExtractableResponse<Response> response) {
        return response.jsonPath()
            .getObject(".", CartItemResponse.class);
    }

    private int extractErrorCode(ExtractableResponse<Response> response) {
        return response.jsonPath().getInt("errorCode");
    }
}
