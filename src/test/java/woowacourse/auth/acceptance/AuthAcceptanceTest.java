package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceFixture.createCustomer;
import static woowacourse.Fixture.다른_비밀번호;
import static woowacourse.Fixture.다른_아이디;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        createCustomer(페퍼);
        String accessToken = RestAssured
                .given().log().all()
                .body(new TokenRequest(페퍼_아이디, 페퍼_비밀번호))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerResponse customer = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        Assertions.assertAll(
                () -> assertThat(customer.getLoginId()).isEqualTo(페퍼_아이디),
                () -> assertThat(customer.getName()).isEqualTo(페퍼_이름)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고
        createCustomer(페퍼);

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(new TokenRequest(다른_아이디, 다른_비밀번호))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/login")
                .then().log().all()
                .extract();

        // then
        // 토큰 발급 요청이 거부된다
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value()),
                () -> assertThat(response.body().asString()).isEqualTo("유효하지 않은 고객입니다")
        );
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면
        createCustomer(페퍼);
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2("FakeToken")
                .when()
                .get("/customers/me")
                .then().log().all()
                .extract();

        // then
        // 내 정보 조회 요청이 거부된다
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
