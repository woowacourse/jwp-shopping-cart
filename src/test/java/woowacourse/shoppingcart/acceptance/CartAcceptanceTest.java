package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static woowacourse.shoppingcart.acceptance.ProductAcceptanceTest.상품_등록되어_있음;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.SignInRequest;
import woowacourse.auth.dto.SignInResponse;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.SignUpRequest;

@DisplayName("장바구니 관련 기능")
public class CartAcceptanceTest extends AcceptanceTest {
    private static final String USER = "alien";

    private Long productId1;
    private Long productId2;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        productId1 = 상품_등록되어_있음("치킨", 10_000, "http://example.com/chicken.jpg");
        productId2 = 상품_등록되어_있음("맥주", 20_000, "http://example.com/beer.jpg");
    }

    @DisplayName("장바구니 아이템 추가")
    @Test
    void addCartItem() {
        String token = 회원가입하고_로그인하여_토큰_가져오기(USER, "alien@email.com", "12345678");

        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(USER, productId1, 3, true, token);

        장바구니_아이템_추가됨(response);
    }

    @DisplayName("장바구니 아이템 목록 조회")
    @Test
    void getCartItems() {
        String token = 회원가입하고_로그인하여_토큰_가져오기(USER, "alien@email.com", "12345678");

        장바구니_아이템_추가되어_있음(USER, productId1, token);
        장바구니_아이템_추가되어_있음(USER, productId2, token);

        ExtractableResponse<Response> response = 장바구니_아이템_목록_조회_요청(USER, token);

        장바구니_아이템_목록_응답됨(response);
        장바구니_아이템_목록_포함됨(response, productId1, productId2);
    }

    @DisplayName("장바구니 삭제")
    @Test
    void deleteCartItem() {
        String token = 회원가입하고_로그인하여_토큰_가져오기(USER, "alien@email.com", "12345678");

        Long cartId = 장바구니_아이템_추가되어_있음(USER, productId1, token);

        ExtractableResponse<Response> response = 장바구니_삭제_요청(USER, cartId, token);

        장바구니_삭제됨(response);
    }

    public static String 회원가입하고_로그인하여_토큰_가져오기(String username, String email, String password) {
        // 회원가입
        SignUpRequest signUpRequest = new SignUpRequest(username, email, password);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when().post("/users")
                .then().log().all();

        // 로그인
        SignInRequest signInRequest = new SignInRequest(email, password);
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signInRequest)
                .when().post("/login")
                .then().log().all()
                .extract().as(SignInResponse.class).getToken();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_추가_요청(String username, Long productId,
                                                               long quantity, boolean checked, String token) {
        AddCartItemRequest requestBody = new AddCartItemRequest(productId, quantity, checked);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + token)
                .body(requestBody)
                .when().post("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_아이템_목록_조회_요청(String username, String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + token)
                .when().get("/cart")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 장바구니_삭제_요청(String username, Long cartId, String token) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + token)
                .body(new DeleteCartItemRequests(List.of(new DeleteCartItemRequest(cartId))))
                .when().delete("/cart")
                .then().log().all()
                .extract();
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static Long 장바구니_아이템_추가되어_있음(String username, Long productId, String token) {
        ExtractableResponse<Response> response = 장바구니_아이템_추가_요청(username, productId, 3, true, token);
        String location = response.header("Location");
        assertThat(location).isNotBlank();
        return Long.parseLong(location.split("/cart/")[1]);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getObject(".", CartResponses.class).getCartItems().stream()
                .map(CartResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
