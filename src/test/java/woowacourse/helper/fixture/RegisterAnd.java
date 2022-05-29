package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.exception.dto.ErrorResponse;

public class RegisterAnd extends Request{

    private final TMember member;

    public RegisterAnd(TMember tMember) {
        tMember.register();
        member = tMember;
    }

    public TokenResponse login() {
        ExtractableResponse<Response> response = login(new TokenRequest(member.getEmail(), member.getPassword()));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        
        TokenResponse tokenResponse = response
                .as(TokenResponse.class);
        member.putToken(tokenResponse.getAccessToken());
        return tokenResponse;
    }

    public ErrorResponse failedLogin(String wrongPassword) {
        ExtractableResponse<Response> response = login(new TokenRequest(member.getEmail(), wrongPassword));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        return response.as(ErrorResponse.class);
    }

    private ExtractableResponse<Response> login(TokenRequest tokenRequest) {
        return post(tokenRequest, "/api/auth");
    }
}
