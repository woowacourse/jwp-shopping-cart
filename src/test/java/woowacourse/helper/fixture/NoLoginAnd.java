package woowacourse.helper.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import woowacourse.exception.dto.ErrorResponse;

public class NoLoginAnd extends Request{

    public ErrorResponse getMyInformation() {
        ExtractableResponse<Response> response = get("/api/members/me");

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        return response.as(ErrorResponse.class);
    }

}
