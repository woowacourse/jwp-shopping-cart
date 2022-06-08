package woowacourse.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static woowacourse.util.HttpRequestUtil.deleteWithAuthorization;
import static woowacourse.util.HttpRequestUtil.get;
import static woowacourse.util.HttpRequestUtil.getWithAuthorization;
import static woowacourse.util.HttpRequestUtil.patchWithAuthorization;
import static woowacourse.util.HttpRequestUtil.post;
import static woowacourse.util.HttpRequestUtil.postWithAuthorization;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.request.MemberCreateRequest;
import woowacourse.shoppingcart.dto.request.MemberUpdateRequest;
import woowacourse.shoppingcart.dto.request.PasswordUpdateRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.EmailUniqueCheckResponse;
import woowacourse.shoppingcart.dto.response.ErrorResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;

public class AcceptanceTestUtil {

    public static ExtractableResponse<Response> 회원가입을_한다(String email, String password, String nickname) {
        이메일_중복체크를_한다(email);
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(email, password, nickname);
        return post("/api/members", memberCreateRequest);
    }

    public static void 이메일_중복체크를_한다(String email) {
        boolean unique = get("/api/members/email-check?email=" + email)
                .as(EmailUniqueCheckResponse.class)
                .isUnique();
        if (!unique) {
            fail("이미 가입된 회원입니다.");
        }
    }

    public static ExtractableResponse<Response> 로그인을_한다(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return post("/api/login", loginRequest);
    }

    public static String 로그인을_하고_토큰을_받는다(String email, String password) {
        return 로그인을_한다(email, password)
                .as(LoginResponse.class)
                .getToken();
    }

    public static ExtractableResponse<Response> 비밀번호를_확인한다(String token, String password) {
        return postWithAuthorization("/api/members/password-check", token, new PasswordCheckRequest(password));
    }

    public static ExtractableResponse<Response> 로그인_없이_비밀번호를_확인한다() {
        return post("/api/members/password-check", new PasswordCheckRequest("1q2w3e4r!"));
    }

    public static ExtractableResponse<Response> preflight_요청을_한다(String url) {
        return RestAssured.given().log().all()
                .when()
                .options(url)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보를_조회한다(String token) {
        return getWithAuthorization("/api/members/me", token);
    }

    public static ExtractableResponse<Response> 로그인_없이_회원_정보를_조회한다() {
        return get("/api/members/me");
    }

    public static ExtractableResponse<Response> 회원_정보를_수정한다(String token, MemberUpdateRequest requestBody) {
        return patchWithAuthorization("/api/members/me", token, requestBody);
    }

    public static ExtractableResponse<Response> 비밀번호를_수정한다(String token, PasswordUpdateRequest requestBody) {
        return patchWithAuthorization("/api/members/password", token, requestBody);
    }

    public static ExtractableResponse<Response> 회원을_삭제한다(String token) {
        return deleteWithAuthorization("/api/members/me", token);
    }

    public static ExtractableResponse<Response> 장바구니에_아이템을_추가한다(String token, Long productId, int quantity) {
        CartItemRequest requestBody = new CartItemRequest(productId, quantity);

        return postWithAuthorization("/api/carts/products", token, requestBody);
    }

    public static ExtractableResponse<Response> 장바구니의_아이템_목록을_조회한다(String token) {
        return getWithAuthorization("/api/carts", token);
    }

    public static ExtractableResponse<Response> 장바구니_아이템_수량을_변경한다(String token, Long productId, int quantity) {
        CartItemRequest requestBody = new CartItemRequest(productId, quantity);
        return patchWithAuthorization("/api/carts/products", token, requestBody);
    }

    public static ExtractableResponse<Response> 장바구니에서_아이템을_삭제한다(String token, Long productId) {
        return deleteWithAuthorization("/api/carts/products?productId=" + productId, token);
    }

    public static void 장바구니_아이템_추가됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 요청_실패함(ExtractableResponse<Response> response, String expectedMessage) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        String errorMessage = response.body()
                .as(ErrorResponse.class)
                .getMessage();
        assertThat(errorMessage).isEqualTo(expectedMessage);
    }

    public static void 장바구니_아이템_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 장바구니_아이템_목록_포함됨(ExtractableResponse<Response> response, Long... productIds) {
        List<Long> resultProductIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(CartItemResponse::getProduct)
                .map(ProductResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).contains(productIds);
    }

    public static void 장바구니_아이템_수량_변경됨(ExtractableResponse<Response> response, Long productId, int expected) {
        CartItemResponse cartItemResponse = response.jsonPath().getList(".", CartItemResponse.class)
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findAny()
                .orElseGet(() -> fail(""));
        assertThat(cartItemResponse.getQuantity())
                .isEqualTo(expected);
    }
}
