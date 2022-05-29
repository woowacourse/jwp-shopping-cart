package woowacourse.helper.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;

public class RegisterAnd extends Request{

    private final TMember member;

    public RegisterAnd(TMember tMember) {
        tMember.register();
        member = tMember;
    }

    public ExtractableResponse<Response> login() {
        return post(new TokenRequest(member.getEmail(), member.getPassword()), "/api/auth");
    }
}
