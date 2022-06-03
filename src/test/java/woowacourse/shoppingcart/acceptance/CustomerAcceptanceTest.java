package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.HashPasswordEncoder;
import woowacourse.shoppingcart.domain.customer.EncodePassword;
import woowacourse.shoppingcart.domain.customer.RawPassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입을 성공적으로 진행한다.")
    @Test
    void addCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky123#", "복희", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("/customers/me"));
    }

    @DisplayName("아이디가 중복되지 않을 때, 아이디 중복 여부를 검사한다.")
    @Test
    void checkDuplicationUserName_unique() {
        signUpCustomer();
        UserNameDuplicationRequest request = new UserNameDuplicationRequest("kth990303");
        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/username/duplication")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("isUnique", equalTo(true));
    }

    @DisplayName("아이디가 중복될 때, 아이디 중복 여부를 검사한다.")
    @Test
    void checkDuplicationUserName_duplicated() {
        signUpCustomer();
        UserNameDuplicationRequest request = new UserNameDuplicationRequest("forky");
        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/username/duplication")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("isUnique", equalTo(false));
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

        assertCustomer(accessToken, "forky", "forky@4321", "복희", 26);
    }

    @DisplayName("내 비밀번호를 제외한 정보를 수정한다.")
    @Test
    void updateMyInfo() {
        signUpCustomer();
        String accessToken = getTokenByLogin();

        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "권복희", 12);
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        assertCustomer(accessToken, "forky", "forky@1234", "권복희", 12);
    }

    private void assertCustomer(String accessToken, String userName, String password, String nickName, int age) {
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CustomerResponse.class);

        assertAll(
                () -> assertThat(customerResponse.getUserName()).isEqualTo(userName),
                () -> assertThat(customerResponse.getPassword()).isEqualTo(encode(password).getPassword()),
                () -> assertThat(customerResponse.getNickName()).isEqualTo(nickName),
                () -> assertThat(customerResponse.getAge()).isEqualTo(age)
        );
    }

    private EncodePassword encode(String rawPassword) {
        RawPassword password = new RawPassword(rawPassword);
        PasswordEncoder passwordEncoder = new HashPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @DisplayName("회원탈퇴를 성공적으로 진행한다.")
    @Test
    void deleteMe() {
        signUpCustomer();
        String accessToken = getTokenByLogin();

        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertInvalidCustomer(accessToken);
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

    private void assertInvalidCustomer(String accessToken) {
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract()
                .as(InvalidCustomerException.class);
    }
}
