package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.auth.utils.Fixture.email;
import static woowacourse.auth.utils.Fixture.nickname;
import static woowacourse.auth.utils.Fixture.password;
import static woowacourse.auth.utils.Fixture.signupRequest;
import static woowacourse.auth.utils.Fixture.tokenRequest;
import static woowacourse.shoppingcart.acceptance.CartAcceptanceTest.장바구니_아이템_추가되어_있음;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static woowacourse.utils.RestAssuredUtils.deleteWithToken;
import static woowacourse.utils.RestAssuredUtils.httpPost;
import static woowacourse.utils.RestAssuredUtils.login;
import static woowacourse.utils.RestAssuredUtils.signOut;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {
    private static final String USER = "puterism";
    private Long cartId1;
    private Long cartId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        Long productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        Long productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");

        cartId1 = 장바구니_아이템_추가되어_있음(USER, productId1);
        cartId2 = 장바구니_아이템_추가되어_있음(USER, productId2);
    }

    @DisplayName("주문하기")
    @Test
    void addOrder() {
        List<OrderRequest> orderRequests = Stream.of(cartId1, cartId2)
                .map(cartId -> new OrderRequest(cartId, 10))
                .collect(Collectors.toList());

        ExtractableResponse<Response> response = 주문하기_요청(USER, orderRequests);

        주문하기_성공함(response);
    }

    @DisplayName("주문 내역 조회")
    @Test
    void getOrders() {
        Long orderId1 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId1, 2)));
        Long orderId2 = 주문하기_요청_성공되어_있음(USER, Collections.singletonList(new OrderRequest(cartId2, 5)));

        ExtractableResponse<Response> response = 주문_내역_조회_요청(USER);

        주문_조회_응답됨(response);
        주문_내역_포함됨(response, orderId1, orderId2);
    }

    @DisplayName("주문 단일 조회")
    @Test
    void getOrder() {
        Long orderId = 주문하기_요청_성공되어_있음(USER, Arrays.asList(
                new OrderRequest(cartId1, 2),
                new OrderRequest(cartId2, 4)
        ));

        ExtractableResponse<Response> response = 주문_단일_조회_요청(USER, orderId);

        주문_조회_응답됨(response);
        주문_조회됨(response, orderId);
    }

    public static ExtractableResponse<Response> 주문하기_요청(String userName, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .when().post("/api/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_내역_조회_요청(String userName) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerName}/orders", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 주문_단일_조회_요청(String userName, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customers/{customerName}/orders/{orderId}", userName, orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 주문하기_요청_성공되어_있음(String userName, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public static void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", Orders.class).stream()
                .map(Orders::getId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    private void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        Orders resultOrder = response.as(Orders.class);
        assertThat(resultOrder.getId()).isEqualTo(orderId);
    }

    @DisplayName("회원관련 기능 인수테스트")
    public static class CustomerAcceptanceTest extends woowacourse.utils.AcceptanceTest {

        @DisplayName("회원가입을 한다.")
        @Test
        void signUpSuccess() {
            // given
            ExtractableResponse<Response> response = httpPost("/customers", signupRequest);

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                    () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email),
                    () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname)
            );
        }

        @DisplayName("토큰이 없을 때 회원 탈퇴를 할 수 없다.")
        @Test
        void signOutNotLogin() {
            // given
            httpPost("/customers", signupRequest);

            // when
            ExtractableResponse<Response> response = signOut("/customers", "");

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @DisplayName("회원 탈퇴를 진행한다.")
        @Test
        void signOutSuccess() {
            // given
            httpPost("/customers", signupRequest);

            ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
            String token = loginResponse.jsonPath().getString("accessToken");

            // when
            SignoutRequest signoutRequest = new SignoutRequest(password);
            ExtractableResponse<Response> response = deleteWithToken("/customers", token, signoutRequest);

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("회원 정보를 수정한다.")
        @Test
        void updateCustomer() {
            // given
            httpPost("/customers", signupRequest);
            ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
            String token = loginResponse.jsonPath().getString("accessToken");

            CustomerUpdateRequest request = new CustomerUpdateRequest("thor", password, "b1234!");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .auth().oauth2(token)
                    .body(request)
                    .when().patch("/customers")
                    .then().log().all().extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo("thor")
            );
        }

        @DisplayName("회원 정보을 조회한다.")
        @Test
        void findCustomer() {
            // given
            httpPost("/customers", signupRequest);
            ExtractableResponse<Response> loginResponse = login("/auth/login", tokenRequest);
            String token = loginResponse.jsonPath().getString("accessToken");

            // when
            ExtractableResponse<Response> response = RestAssured.given().log().all()
                    .auth().oauth2(token)
                    .when().get("/customers")
                    .then().log().all().extract();

            // then
            assertAll(
                    () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                    () -> assertThat(response.jsonPath().getString("nickname")).isEqualTo(nickname),
                    () -> assertThat(response.jsonPath().getString("email")).isEqualTo(email)
            );
        }
    }
}
