package woowacourse.shoppingcart.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import woowacourse.acceptance.AcceptanceTest;
import woowacourse.acceptance.RestAssuredFixture;
import woowacourse.auth.dto.LogInRequest;
import woowacourse.shoppingcart.dto.CartProductRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private String token;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        ProductRequest productRequest = new ProductRequest("치킨", 10000, "http://example.com/chicken.jpg");
        ProductRequest productRequest2 = new ProductRequest("피자", 20000, "http://example.com/pizza.jpg");
        RestAssuredFixture.post(productRequest, "/products", HttpStatus.CREATED.value());
        RestAssuredFixture.post(productRequest2, "/products", HttpStatus.CREATED.value());

        SignUpRequest signUpRequest = new SignUpRequest("rennon", "rennon@woowa.com", "123456q!");
        RestAssuredFixture.post(signUpRequest, "/users", HttpStatus.CREATED.value());

        LogInRequest logInRequest = new LogInRequest("rennon@woowa.com", "123456q!");
        token = RestAssuredFixture.getSignInResponse(logInRequest, "/login").getToken();

        //when & then
        RestAssuredFixture.postCart(new CartProductRequest(1L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
        RestAssuredFixture.postCart(new CartProductRequest(2L, 1L, true),
                token, "/cart", HttpStatus.CREATED.value());
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        //givn
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        //when & then
        RestAssuredFixture.postCart(orderRequests, token, "/orders", HttpStatus.CREATED.value());
    }


    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        //given
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        RestAssuredFixture.postCart(orderRequests, token, "/orders", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.get(token, "/orders", HttpStatus.OK.value());
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        //givn
        List<OrderRequest> orderRequests = Stream.of(1L, 2L)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());
        RestAssuredFixture.postCart(orderRequests, token, "/orders", HttpStatus.CREATED.value());

        //when & then
        RestAssuredFixture.get(token, "/orders/" + 1L, HttpStatus.OK.value())
                .body("orderDetails.size()", is(2));
    }
}
