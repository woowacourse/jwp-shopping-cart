package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;

public class NoLoginAnd extends Request{

    private final TMember tMember;

    public NoLoginAnd(TMember tMember) {
        this.tMember = tMember;
    }

    public ErrorResponse getMyInformation() {
        ExtractableResponse<Response> response = get("/api/members/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }

    public ErrorResponse updateName(String name) {
        ExtractableResponse<Response> response = put(new MemberNameUpdateRequest(name), "/api/members/me/name");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }

    public ErrorResponse updateMyPassword(String password) {
        ExtractableResponse<Response> response = put(new MemberPasswordUpdateRequest(tMember.getPassword(), password),
                "/api/members/me/name");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }

    public ErrorResponse deleteMember() {
        ExtractableResponse<Response> response = delete("/api/members/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }

}
