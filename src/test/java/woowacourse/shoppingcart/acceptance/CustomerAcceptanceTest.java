package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입을 성공적으로 진행한다.")
    @Test
    void addCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky123#","복희",  26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("/customers/me"));
    }

    @DisplayName("로그인한 회원이 자신의 정보를 조회한다.")
    @Test
    void getMe() {
        signUpCustomer();
        String accessToken = getTokenByLogin();
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);

        assertAll(
                () -> assertThat(customerResponse.getUserName()).isEqualTo("forky"),
                () -> assertThat(customerResponse.getNickName()).isEqualTo("복희"),
                () -> assertThat(customerResponse.getAge()).isEqualTo(26)
        );
    }

    @DisplayName("로그인하지 않은 상태로 자신의 정보를 조회한다.")
    @Test
    void getMe_unauthorized() {
        Exception exception = RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract()
                .as(Exception.class);

        assertThat(exception).hasMessageContaining("로그인");
    }

    @DisplayName("내 비밀번호를 수정한다.")
    @Test
    void updateMyPassword() {
        signUpCustomer();
        String accessToken = getTokenByLogin();

        PasswordRequest passwordRequest = new PasswordRequest("forky@1234", "forky@4321");
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(passwordRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/customers/me/password")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMyInfo() {
        signUpCustomer();
        String accessToken = getTokenByLogin();
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        signUpCustomer();
        String accessToken = getTokenByLogin();
    }

    private String getTokenByLogin() {
        return RestAssured.given().log().all()
                .body(new TokenRequest("forky", "forky@1234"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .extract()
                .as(TokenResponse.class)
                .getAccessToken();
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
