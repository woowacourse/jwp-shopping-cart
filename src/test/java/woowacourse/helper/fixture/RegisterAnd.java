package woowacourse.helper.fixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;

public class RegisterAnd extends Request{

    private final TMember member;

    public RegisterAnd(TMember tMember) {
        tMember.register();
        member = tMember;
    }

    public TokenResponse login(int status) {
        TokenResponse response = login(new TokenRequest(member.getEmail(), member.getPassword()))
                .as(TokenResponse.class);

        member.putToken(response.getAccessToken());
        return response;
    }

    private ExtractableResponse<Response> login(TokenRequest tokenRequest) {
        return post(tokenRequest, "/api/auth");
    }
}
