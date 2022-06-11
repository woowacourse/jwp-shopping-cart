package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.fixture.Fixture.BEARER;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.SignInResponseDto;
import woowacourse.shoppingcart.dto.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.OrdersDetailDto;
import woowacourse.shoppingcart.dto.OrdersDetailProductResponseDto;
import woowacourse.shoppingcart.dto.OrdersRequestDto;
import woowacourse.shoppingcart.dto.OrdersResponseDto;
import woowacourse.shoppingcart.dto.ProductRequestDto;
import woowacourse.shoppingcart.dto.SignUpDto;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private static final int COUNT1 = 1;
    private static final int COUNT2 = 2;

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

    @BeforeEach
    void setCartItems() {
        final AddCartItemRequestDto addCartItemRequestDto1 = new AddCartItemRequestDto(productId1, COUNT1);
        final AddCartItemRequestDto addCartItemRequestDto2 = new AddCartItemRequestDto(productId2, COUNT2);

        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto1);
        post("/api/customers/" + customerId + "/carts", authorizationHeader, addCartItemRequestDto2);
    }

    @Test
    @DisplayName("상품 id 목록으로 장바구니에 담긴 상품 주문을 요청한다.")
    void order() {
        // given
        final OrdersRequestDto ordersRequestDto = new OrdersRequestDto(List.of(productId1, productId2));

        // when
        final ExtractableResponse<Response> response
                = post("/api/customers/" + customerId + "/orders", authorizationHeader, ordersRequestDto);

        // then
        final ExtractableResponse<Response> orderResponse
                = get(response.header(HttpHeaders.LOCATION), authorizationHeader);
        final OrdersResponseDto ordersResponseDto = orderResponse.body().as(OrdersResponseDto.class);

        final List<OrdersDetailProductResponseDto> products = ordersResponseDto.getOrdersDetails()
                .stream()
                .map(OrdersDetailDto::getProduct)
                .collect(Collectors.toList());

        final List<Long> productIds = products
                .stream()
                .map(OrdersDetailProductResponseDto::getProductId)
                .collect(Collectors.toList());

        final int count1 = ordersResponseDto.getOrdersDetails()
                .stream()
                .filter(ordersDetailDto -> ordersDetailDto.getProduct().getProductId().equals(productId1))
                .findAny()
                .map(OrdersDetailDto::getCount).get();

        final int count2 = ordersResponseDto.getOrdersDetails()
                .stream()
                .filter(ordersDetailDto -> ordersDetailDto.getProduct().getProductId().equals(productId2))
                .findAny()
                .map(OrdersDetailDto::getCount).get();

        assertAll(
                () -> assertThat(ordersResponseDto.getOrdersDetails().size()).isEqualTo(2),
                () -> assertThat(productIds).contains(productId1, productId2),
                () -> assertThat(count1).isEqualTo(COUNT1),
                () -> assertThat(count2).isEqualTo(COUNT2)
        );
    }

    @Test
    @DisplayName("상품 id 목록으로 장바구니에 담긴 상품 주문을 요청할 때 카트에 없는 상품 id로 요청한다.")
    void order_notFoundOrder() {
        // given
        final OrdersRequestDto ordersRequestDto = new OrdersRequestDto(List.of(productId1, 1000L));

        // when
        final ExtractableResponse<Response> response
                = post("/api/customers/" + customerId + "/orders", authorizationHeader, ordersRequestDto);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
