package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = 회원정보_조회(accessToken);
        CustomerResponse customerResponse = response.as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getEmail()).isEqualTo("newemail@email.com"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("파리채")
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고
        회원가입(파리채);

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new TokenRequest("email@email.com", "password321!"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/login")
                .then().log().all()
                .extract();

        // then
        // 토큰 발급 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        ExtractableResponse<Response> response = 회원정보_조회("accessToken");

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
