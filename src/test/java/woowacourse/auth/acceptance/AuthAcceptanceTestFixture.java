package woowacourse.auth.acceptance;

import woowacourse.auth.ui.dto.request.LoginRequest;
import woowacourse.auth.ui.dto.request.MemberCreateRequest;
import woowacourse.auth.ui.dto.request.MemberUpdateRequest;
import woowacourse.auth.ui.dto.request.PasswordRequest;

public class AuthAcceptanceTestFixture {

    static final MemberCreateRequest MEMBER_CREATE_REQUEST =
            new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
    static final LoginRequest VALID_LOGIN_REQUEST = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");
    static final PasswordRequest VALID_PASSWORD_CHECK_REQUEST = new PasswordRequest("1q2w3e4r!");
    static final PasswordRequest VALID_PASSWORD_UPDATE_REQUEST = new PasswordRequest("1q2w3e4r@");
    static final MemberUpdateRequest VALID_NICKNAME_UPDATE_REQUEST = new MemberUpdateRequest("바뀐닉네임");
    static final String DATA_EMPTY_EXCEPTION_MESSAGE = "입력하지 않은 정보가 있습니다.";

    static final String LOGIN_URI = "/api/login";
    static final String SIGN_UP_URI = "/api/members";
    static final String EMAIL_DUPLICATION_CHECK_URI = "/api/members/check-email?email=";
    static final String PASSWORD_UPDATE_URI = "/api/members/password";
    static final String PASSWORD_CHECK_URI = "/api/members/password-check";
    static final String MEMBERS_URI = "/api/members/me";
}
