package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.member.dto.MemberDeleteRequest;
import woowacourse.member.dto.MemberNameUpdateRequest;
import woowacourse.member.dto.MemberPasswordUpdateRequest;

public class NoLoginAnd extends Request {

    public ErrorResponse getMyInformation() {
        ExtractableResponse<Response> response = get("/api/members/me");

        return checkUnauthorizedAndReturnErrorResponse(response);
    }

    public ErrorResponse updateMyName(String name) {
        MemberNameUpdateRequest memberNameUpdateRequest = new MemberNameUpdateRequest(name);
        ExtractableResponse<Response> response = put(memberNameUpdateRequest, "/api/members/me/name");

        return checkUnauthorizedAndReturnErrorResponse(response);
    }

    public ErrorResponse updateMyPassword(String password) {
        MemberPasswordUpdateRequest memberPasswordUpdateRequest = new MemberPasswordUpdateRequest(PASSWORD, password);
        ExtractableResponse<Response> response = put(memberPasswordUpdateRequest, "/api/members/me/password");

        return checkUnauthorizedAndReturnErrorResponse(response);
    }

    public ErrorResponse deleteMember() {
        MemberDeleteRequest memberDeleteRequest = new MemberDeleteRequest(PASSWORD);
        ExtractableResponse<Response> response = delete(memberDeleteRequest, "/api/members/me");

        return checkUnauthorizedAndReturnErrorResponse(response);
    }

    private ErrorResponse checkUnauthorizedAndReturnErrorResponse(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }
}
