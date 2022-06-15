package woowacourse.shoppingcart.auth.acceptance;

import static woowacourse.shoppingcart.customer.acceptance.CustomerRestHandler.회원가입;
import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.auth.application.dto.request.TokenRequest;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.support.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class AuthRestHandler extends RestHandler {

    private static final String BASE_URL = "/auth";

    public static ExtractableResponse<Response> 로그인(final String email, final String password) {
        return postRequest(BASE_URL + "/login", new TokenRequest(email, password));
    }

    public static ExtractableResponse<Response> 회원가입_로그인() {
        return 회원가입_로그인(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
    }

    public static ExtractableResponse<Response> 회원가입_로그인(final String email, final String nickname,
                                                         final String password) {
        회원가입(email, nickname, password);
        return 로그인(email, password);
    }

    public static ExtractableResponse<Response> 로그아웃(final String accessToken) {
        return postRequest(BASE_URL + "/logout", accessToken);
    }

    public static void assertThatAuthException(final ExtractableResponse<Response> response,
                                           final AuthExceptionCode exceptionCode) {
        RestHandler.assertThatException(response, exceptionCode);
    }
}
