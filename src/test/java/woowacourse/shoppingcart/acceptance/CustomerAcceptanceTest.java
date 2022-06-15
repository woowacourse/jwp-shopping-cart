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
import woowacourse.shoppingcart.dto.customer.CustomerProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일 중복 체크 후 회원가입")
    @Test
    void addMember() {
        assertThat(회원가입(파리채).statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);

        ExtractableResponse<Response> response = 회원정보_조회(accessToken);
        CustomerResponse customerResponse = response.as(CustomerResponse.class);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getEmail()).isEqualTo("newemail@email.com"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("파리채")
        );
    }

    @DisplayName("내 닉네임 수정")
    @Test
    void updateNickname() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);
        회원정보_조회(accessToken);

        CustomerProfileRequest customerProfileRequest = new CustomerProfileRequest("수정파리채");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(customerProfileRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/members/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("내 비밀번호 수정")
    @Test
    void updatePassword() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);
        회원정보_조회(accessToken);

        PasswordRequest passwordRequest = new PasswordRequest("newpassword123!");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(passwordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/api/members/password")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        회원가입(파리채);
        String accessToken = 로그인_후_토큰발급(파리채토큰);
        회원정보_조회(accessToken);

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/api/members/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
