package woowacourse.shoppingcart.customer.acceptance;

import static woowacourse.support.TextFixture.EMAIL_VALUE;
import static woowacourse.support.TextFixture.NICKNAME_VALUE;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRemoveRequest;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.support.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class CustomerRestHandler extends RestHandler {

    private static final String BASE_URL = "/customers";

    public static ExtractableResponse<Response> 회원가입() {
        return 회원가입(EMAIL_VALUE, NICKNAME_VALUE, PASSWORD_VALUE);
    }

    public static ExtractableResponse<Response> 회원가입(final String email, final String nickname, final String password) {
        return postRequest(BASE_URL, new CustomerRegisterRequest(email, nickname, password));
    }

    public static ExtractableResponse<Response> 개인정보조회(final String accessToken) {
        return getRequest(BASE_URL, accessToken);
    }

    public static ExtractableResponse<Response> 개인정보변경(final CustomerProfileUpdateRequest request,
                                                       final String accessToken) {
        return patchRequest(BASE_URL + "/profile", request, accessToken);
    }

    public static ExtractableResponse<Response> 비밀번호변경(final CustomerPasswordUpdateRequest request,
                                                       final String accessToken) {
        return patchRequest(BASE_URL + "/password", request, accessToken);
    }

    public static ExtractableResponse<Response> 회원탈퇴(final CustomerRemoveRequest request, final String accessToken) {
        return deleteRequest(BASE_URL, request, accessToken);
    }

    public static <T> void assertThatException(final ExtractableResponse<Response> response,
                                               final CustomerExceptionCode exceptionCode) {
        RestHandler.assertThatException(response, exceptionCode);
    }
}
