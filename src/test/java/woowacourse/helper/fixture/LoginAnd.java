package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;
import woowacourse.member.dto.MemberResponse;

public class LoginAnd extends Request {

    private final TMember tMember;

    public LoginAnd(TMember tMember) {
        tMember.login();
        this.tMember = tMember;
    }

    public MemberResponse getMyInformation() {
        ExtractableResponse<Response> response = getWithToken("/api/members/me", tMember.getToken());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        return response.as(MemberResponse.class);
    }

    public ExtractableResponse<Response> updateMyName(final String name) {
        MemberNameUpdateRequest memberNameUpdateRequest = new MemberNameUpdateRequest(name);
        return putWithToken(memberNameUpdateRequest, "/api/members/me/name", tMember.getToken());
    }

    public ExtractableResponse<Response> updateMyPassword(final String password) {
        MemberPasswordUpdateRequest memberPasswordUpdateRequest =
                new MemberPasswordUpdateRequest(tMember.getPassword(), password);
        return putWithToken(memberPasswordUpdateRequest, "/api/members/me/password", tMember.getToken());
    }

    public ExtractableResponse<Response> deleteMember() {
        return deleteWithToken("/api/members/me", tMember.getToken());
    }
}
