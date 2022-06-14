package woowacourse.shoppingcart.acceptance;

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
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDetailResponse;
import woowacourse.shoppingcart.dto.customer.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "djwhy5510@naver.com";
    private static final String PASSWORD = "1234567891";

    @DisplayName("회원가입")
    @Test
    void register() {
        // given, when 이름, 이메일, 비밀번호를 입력하고 회원 등록을 요청하면
        ExtractableResponse<Response> response = requestPostWithBody("/api/customer",
                new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));

        // then 회원이 성공적으로 등록된다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void showMyDetail() {
        // given 회원가입 후 로그인하여 토큰을 발급받고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = loginAndGetToken();

        // when 내 정보를 조회하면
        final ExtractableResponse<Response> response = showDetail(accessToken);

        // then 성공적으로 정보를 조회한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.as(CustomerDetailResponse.class)).usingRecursiveComparison()
                        .isEqualTo(new CustomerDetailResponse(NAME, EMAIL))
        );
    }

    private ExtractableResponse<Response> showDetail(final String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/customer")
                .then().log().all()
                .extract();
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMyDetail() {
        // given 회원가입 후 로그인하여 토큰을 발급받고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = loginAndGetToken();

        // when 회원 정보를 수정하면
        final ExtractableResponse<Response> response = requestPutWithTokenAndBody("/api/customer/profile",
                accessToken, new CustomerProfileUpdateRequest("썬"));

        // then 성공적으로 회원 정보가 수정된다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("내 비밀번호 수정")
    @Test
    void updatePassword() {
        // given 회원가입 후 로그인하여 토큰을 발급받고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = loginAndGetToken();

        // when 비밀번호를 수정하면
        final ExtractableResponse<Response> response = requestPutWithTokenAndBody("/api/customer/password",
                accessToken, new CustomerPasswordUpdateRequest(PASSWORD, "newpassword123"));

        // then 성공적으로 비밀번호가 수정된다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given 회원가입 후 로그인하여 토큰을 발급받고
        requestPostWithBody("/api/customer", new CustomerRegisterRequest(NAME, EMAIL, PASSWORD));
        final String accessToken = loginAndGetToken();

        // when 비밀번호를 입력하고 회원 탈퇴를 하면
        final ExtractableResponse<Response> response = requestDeleteWithTokenAndBody("/api/customer",
                accessToken, new CustomerDeleteRequest(PASSWORD));

        // then 성공적으로 회원 탈퇴가 된다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    private String loginAndGetToken() {
        return requestPostWithBody("/api/login", new TokenRequest(EMAIL, PASSWORD))
                .as(TokenResponse.class)
                .getAccessToken();
    }
}
