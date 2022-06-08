package woowacourse.member.acceptance;

import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.MemberCreateRequest;
import woowacourse.member.dto.request.MemberUpdateRequest;
import woowacourse.member.dto.request.PasswordRequest;

public class MemberAcceptanceTestFixture {

    public static final MemberCreateRequest MEMBER_CREATE_REQUEST =
            new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "닉네임");
    public static final LoginRequest VALID_LOGIN_REQUEST = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");
    public static final PasswordRequest VALID_PASSWORD_CHECK_REQUEST = new PasswordRequest("1q2w3e4r!");
    public static final PasswordRequest VALID_PASSWORD_UPDATE_REQUEST = new PasswordRequest("1q2w3e4r@");
    public static final MemberUpdateRequest VALID_NICKNAME_UPDATE_REQUEST = new MemberUpdateRequest("바뀐닉네임");
    public static final String DATA_EMPTY_EXCEPTION_MESSAGE = "입력하지 않은 정보가 있습니다.";

    public static final String LOGIN_URI = "/api/login";
    public static final String SIGN_UP_URI = "/api/members";
    public static final String EMAIL_DUPLICATION_CHECK_URI = "/api/members/email-check?email=";
    public static final String PASSWORD_UPDATE_URI = "/api/members/password";
    public static final String PASSWORD_CHECK_URI = "/api/members/password-check";
    public static final String MEMBERS_URI = "/api/members/me";
}
