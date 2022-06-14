package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import woowacourse.shoppingcart.domain.OrderResponse;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected String getValue(ExtractableResponse<Response> response, String value) {
        return response.body().jsonPath().getString(value);
    }

    protected String getPhoneNumberValue(ExtractableResponse<Response> response, String value) {
        return String.valueOf(response.body().jsonPath().getMap("phoneNumber").get(value));
    }

    protected Map<String, Object> 회원_정보(String account, String nickname, String password,
            String address, String start, String middle, String last) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("nickname", nickname);
        request.put("password", password);
        request.put("address", address);
        request.put("phoneNumber", 휴대폰_정보(start, middle, last));
        return request;
    }

    protected Map<String, String> 휴대폰_정보(String start, String middle, String last) {
        Map<String, String> phoneNumber = new HashMap<>();
        phoneNumber.put("start", start);
        phoneNumber.put("middle", middle);
        phoneNumber.put("last", last);
        return phoneNumber;
    }

    protected ExtractableResponse<Response> 회원_가입_요청(Map<String, Object> request) {
        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signup")
                .then()
                .log().all()
                .extract();
    }

    protected ExtractableResponse<Response> 토큰_발급(String account, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("account", account);
        request.put("password", password);

        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/signin")
                .then()
                .log().all()
                .extract();
    }

    protected String 회원_가입_후_토큰_발급(String account, String password) {
        회원이_저장되어_있음(account, password);

        return getValue(토큰_발급(account, password), "accessToken");
    }

    protected ExtractableResponse<Response> 회원_조회(String accessToken) {
        ExtractableResponse<Response> response = RestAssured.given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/customers")
                .then()
                .log().all()
                .extract();
        return response;
    }

    protected ExtractableResponse<Response> 회원이_저장되어_있음(String account, String password) {
        return 회원_가입_요청(회원_정보(account,
                "에덴",
                password,
                "에덴 동산",
                "010",
                "1234",
                "5678"));
    }

    protected ExtractableResponse<Response> 회원_탈퇴(String accessToken, String password) {
        Map<String, Object> request = new HashMap<>();
        request.put("password", password);

        return RestAssured.given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer" + accessToken)
                .body(request)
                .when()
                .delete("/customers")
                .then()
                .log().all()
                .extract();
    }

    protected Map<String, Object> 회원_수정_정보(String nickname, String address, String start,
            String middle, String last) {
        Map<String, Object> request = new HashMap<>();
        request.put("nickname", nickname);
        request.put("address", address);
        request.put("phoneNumber", 휴대폰_정보(start, middle, last));
        return request;
    }

    protected ExtractableResponse<Response> 회원_수정(String accessToken, Map<String, Object> request) {
        return RestAssured.given()
                .log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .put("/customers")
                .then()
                .log().all()
                .extract();
    }

    public ExtractableResponse<Response> 장바구니_아이템_추가_요청(String userName, Long productId) {
        String accessToken = 회원_가입_후_토큰_발급("hoho", "Abc1234!");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId", productId);

        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when().post("/customers/cart")
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/cart")
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long cartId) {
        return RestAssured
                .given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/cart/{cartId}", cartId)
                .then().log().all()
                .extract();
    }

    public void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/cart/")[1]);
    }

    public void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        CartResponse cartResponse = response.jsonPath().getObject(".", CartResponse.class);
        assertThat(cartResponse.getCart()).extracting("id").contains(productIds);
    }

    public void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public ExtractableResponse<Response> 주문하기_요청(String accessToken, List<OrderRequest> orderRequests) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequests)
                .header("Authorization", "Bearer " + accessToken)
                .when().post("/customers/orders")
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 주문_내역_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/customers/orders")
                .then().log().all()
                .extract();
    }

    public ExtractableResponse<Response> 주문_단일_조회_요청(String accessToken, Long orderId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/customers/orders/{orderId}", orderId)
                .then().log().all()
                .extract();
    }

    public void 주문하기_성공함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public Long 주문하기_요청_성공되어_있음(String userName, List<OrderRequest> orderRequests) {
        ExtractableResponse<Response> response = 주문하기_요청(userName, orderRequests);
        return Long.parseLong(response.header("Location").split("/orders/")[1]);
    }

    public void 주문_조회_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 주문_내역_포함됨(ExtractableResponse<Response> response, Long... orderIds) {
        List<Long> resultOrderIds = response.jsonPath().getList(".", OrderResponse.class).stream()
                .map(OrderResponse::getOrderId)
                .collect(Collectors.toList());
        assertThat(resultOrderIds).contains(orderIds);
    }

    public void 주문_조회됨(ExtractableResponse<Response> response, Long orderId) {
        OrderResponse resultOrder = response.as(OrderResponse.class);
        assertThat(resultOrder.getOrderId()).isEqualTo(orderId);
    }
}
