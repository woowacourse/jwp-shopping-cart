package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.application.dto.CartItemResponse;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.ui.dto.CartItemQuantityRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.shoppingcart.TCustomer.ROOKIE;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {

    private String accessToken;
    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        ROOKIE.signUp();
        accessToken = ROOKIE.signIn().getAccessToken();
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void showCartItems() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @Test
    @DisplayName("장바구니에 등록된 상품의 수량 변경")
    void updateQuantityCartItem() {
        // given
        장바구니_아이템_추가되어_있음(accessToken, productId1);
        장바구니_아이템_추가되어_있음(accessToken, productId2);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_수량_변경_요청(accessToken, productId1, 2);

        // then
        장바구니_아이템_수량_변경_응답됨(response);
//        장바구니_아이템_수량_변경_확인됨(accessToken);
    }


    @DisplayName("장바구니의 아이템 단일 삭제")
    @Test
    void deleteCartItem() {
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_단일_삭제_요청(accessToken, productId1);

        장바구니_삭제됨(response);
    }

    @Test
    @DisplayName("장바구니에 이미 등록된 상품을 다시 등록하는 경우 예외가 발생한다.")
    void addCartDuplicatedItem() {
        // given
        장바구니_아이템_추가되어_있음(accessToken, productId1);

        // when
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        // then
        장바구니에_중복된_아이템을_추가하면_예외가_발생함(response);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().post("/api/carts/products/{id}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_단일_삭제_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().delete("/api/carts/products/{id}", productId)
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_추가되어_있음(String accessToken, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(it -> it.getId())
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    private ExtractableResponse<Response> 장바구니_아이템_수량_변경_요청(String accessToken, Long productId, int quantity) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(new CartItemQuantityRequest(quantity))
                .when().patch("/api/carts/products/{id}", productId)
                .then().log().all()
                .extract();
    }

    private void 장바구니_아이템_수량_변경_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private void 장바구니에_중복된_아이템을_추가하면_예외가_발생함(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
