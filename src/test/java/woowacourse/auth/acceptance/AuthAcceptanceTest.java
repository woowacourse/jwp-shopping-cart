package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.AuthorizationException;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    void init() {
        signUpCustomer();
    }

    @DisplayName("로그인에 성공할 때 토큰을 발급한다.")
    @Test
    void myInfoWithBearerAuth() {
        // given
        String accessToken = RestAssured.given().log().all()
                .body(new TokenRequest("forky", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
        // when
        CustomerResponse actual = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);
        // then
        assertAll(
                () -> assertThat(actual.getUsername()).isEqualTo("forky")
        );
    }

    @DisplayName("아이디가 일치하지 않아 로그인에 실패하는 경우는 토큰 발급 요청이 거부된다.")
    @Test
    void myInfoWithBadBearerAuthByUserName() {
        // when
        RestAssured.given().log().all()
                .body(new TokenRequest("kth990303", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(InvalidArgumentRequestException.class);
    }

    @DisplayName("비밀번호가 일치하지 않아 로그인에 실패하는 경우는 토큰 발급 요청이 거부된다.")
    @Test
    void myInfoWithBadBearerAuthByPassword() {
        // when
        RestAssured.given().log().all()
                .body(new TokenRequest("forky", "kth@990303"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(InvalidArgumentRequestException.class);
    }

    @DisplayName("유효하지 않은 토큰으로 회원 관련 기능에 접근할 경우 요청이 거부된다.")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        RestAssured.given().log().all()
                .auth().oauth2("invalidToken")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                // then
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract()
                .as(AuthorizationException.class);
    }

    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers")
                .then().log().all();
    }
}
