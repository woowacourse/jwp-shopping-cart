package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

@DisplayName("장바구니 관련 기능")
@SuppressWarnings("NonAsciiCharacters")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "awesomeo@gmail.com";
    private static final String NICKNAME = "awesome";
    private static final String PASSWORD = "Password123!";

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        createCustomer(new CreateCustomerRequest(EMAIL, NICKNAME, PASSWORD));
        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String accessToken = 로그인_요청(new TokenRequest(EMAIL, PASSWORD));

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);
        List<CartItemResponse> cartItemResponses = 장바구니_아이템_목록_조회(accessToken);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(cartItemResponses).usingRecursiveComparison()
                        .isEqualTo(List.of(new CartItemResponse(productId1, "치킨", 10_000, 1, "http://example.com/chicken.jpg")))
        );
    }

    @DisplayName("동일 아이템 중복 추가")
    @Test
    void addDuplicateItem() {
        // 로그인 유저가 장바구니에 아이템을 담고
        String accessToken = 로그인_요청(new TokenRequest(EMAIL, PASSWORD));
        장바구니_아이템_추가_요청(accessToken, productId1);

        // 동일한 아이템을 다시 담으면
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(accessToken, productId1);

        // 400 응답과 에러 메시지를 반환한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 추가된 상품입니다.")
        );
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String accessToken = 로그인_요청(new TokenRequest(EMAIL, PASSWORD));

        장바구니_아이템_추가_요청(accessToken, productId1);
        장바구니_아이템_추가_요청(accessToken, productId2);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);
        List<CartItemResponse> cartItemResponses = 장바구니_아이템_목록_추출(response);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(cartItemResponses).usingRecursiveComparison().isEqualTo(
                        List.of(new CartItemResponse(productId1, "치킨", 10_000, 1, "http://example.com/chicken.jpg"),
                                new CartItemResponse(productId2, "맥주", 20_000, 1, "http://example.com/beer.jpg"))
                )
        );
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다.")
    @Test
    void addCartItemQuantity() {
        String accessToken = 로그인_요청(new TokenRequest(EMAIL, PASSWORD));
        장바구니_아이템_추가_요청(accessToken, productId1);

        ExtractableResponse<Response> response = 상품_수량_업데이트_요청(accessToken, 2);
        List<CartItemResponse> cartItemResponses = 장바구니_아이템_목록_조회(accessToken);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(cartItemResponses).usingRecursiveComparison().isEqualTo(
                        List.of(new CartItemResponse(productId1, "치킨", 10_000, 2, "http://example.com/chicken.jpg"))
                )
        );
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String accessToken = 로그인_요청(new TokenRequest(EMAIL, PASSWORD));
        장바구니_아이템_추가_요청(accessToken, productId1);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(accessToken, productId1);
        List<CartItemResponse> cartItemResponses = 장바구니_아이템_목록_조회(accessToken);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(cartItemResponses.size()).isEqualTo(0)
        );
    }

    public ExtractableResponse<Response> 상품_수량_업데이트_요청(String accessToken, int quantity) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new UpdateQuantityRequest(quantity))
                .when().log().all()
                .patch("/api/carts/products/{productId}", productId1)
                .then().log().all()
                .extract();
        return response;
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().post("/api/carts/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/carts")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String accessToken, Long productId) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/api/carts/products/{productId}", productId)
                .then().log().all()
                .extract();
    }

    public static List<CartItemResponse> 장바구니_아이템_목록_조회(String accessToken) {
        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(accessToken);
        return 장바구니_아이템_목록_추출(response);
    }

    public static List<CartItemResponse> 장바구니_아이템_목록_추출(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getList(".", CartItemResponse.class);
    }

    public static Long 장바구니_아이템_추가되어_있음(String userName, Long productId) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(userName, productId);
        return Long.parseLong(response.header("Location").split("/carts/")[1]);
    }
}
