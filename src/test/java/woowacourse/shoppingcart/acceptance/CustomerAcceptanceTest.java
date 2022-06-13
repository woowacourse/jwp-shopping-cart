package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.fixture.SimpleResponse;
import woowacourse.fixture.SimpleRestAssured;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입을 성공적으로 진행한다.")
    @Test
    void addCustomer() {
        //given
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky123#", "복희", 26);

        //when
        SimpleResponse response = SimpleRestAssured.post("/customers", customerRequest);

        //then
        response.assertStatus(HttpStatus.CREATED);
        response.assertHeader("Location", "/customers/me");
    }

    @DisplayName("아이디가 중복되지 않을 때, 아이디 중복 여부를 검사한다.")
    @Test
    void checkDuplicationUserName_unique() {
        //given & when
        SimpleResponse response = SimpleRestAssured.getWithParam("/customers/username/uniqueness",
                "username", "pocky");
        //then
        response.assertStatus(HttpStatus.OK);
        response.assertBody("isUnique", true);
    }

    @DisplayName("아이디가 중복될 때, 아이디 중복 여부를 검사한다.")
    @Test
    void checkDuplicationUserName_duplicated() {
        //given
        signUpCustomer();

        //when
        SimpleResponse response = SimpleRestAssured.getWithParam("/customers/username/uniqueness",
                "username", "forky");

        //then
        response.assertStatus(HttpStatus.OK);
        response.assertBody("isUnique", false);
    }

    @DisplayName("로그인한 회원이 자신의 정보를 조회한다.")
    @Test
    void getMe() {
        //given
        signUpCustomer();
        String accessToken = getTokenByLogin();

        //when
        SimpleResponse response = SimpleRestAssured.getWithToken("/customers/me", accessToken);

        //then
        CustomerResponse customerResponse = response.toObject(CustomerResponse.class);
        assertAll(
                () -> response.assertStatus(HttpStatus.OK),
                () -> assertThat(customerResponse.getUsername()).isEqualTo("forky"),
                () -> assertThat(customerResponse.getNickname()).isEqualTo("복희"),
                () -> assertThat(customerResponse.getAge()).isEqualTo(26)
        );
    }

    @DisplayName("로그인하지 않은 상태로 자신의 정보를 조회한다.")
    @Test
    void getMe_unauthorized() {
        //given & when
        SimpleResponse response = SimpleRestAssured.get("/customers/me");

        //then
        response.assertStatus(HttpStatus.UNAUTHORIZED);
        assertThat(response.containsExceptionMessage("로그인")).isTrue();
    }

    @DisplayName("내 비밀번호를 수정한다.")
    @Test
    void updateMyPassword() {
        //given
        signUpCustomer();
        String accessToken = getTokenByLogin();
        PasswordRequest passwordRequest = new PasswordRequest("forky@1234", "forky@4321");

        //when
        SimpleResponse response = SimpleRestAssured.putWithToken("/customers/me/password",
                accessToken, passwordRequest);

        //then
        response.assertStatus(HttpStatus.OK);
        assertCustomer(accessToken, "forky", "forky@4321", "복희", 26);
    }

    @DisplayName("내 비밀번호를 제외한 정보를 수정한다.")
    @Test
    void updateMyInfo() {
        //given
        signUpCustomer();
        String accessToken = getTokenByLogin();
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "권복희", 12);

        //when
        SimpleResponse response = SimpleRestAssured.putWithToken("/customers/me", accessToken, customerRequest);

        //then
        response.assertStatus(HttpStatus.OK);
        assertCustomer(accessToken, "forky", "forky@1234", "권복희", 12);
    }

    @DisplayName("회원탈퇴를 성공적으로 진행한다.")
    @Test
    void deleteMe() {
        //given
        signUpCustomer();
        String accessToken = getTokenByLogin();

        //when
        SimpleResponse response = SimpleRestAssured.deleteWithToken("/customers/me", accessToken);

        //then
        response.assertStatus(HttpStatus.NO_CONTENT);
        assertInvalidCustomer(accessToken);
    }

    private void signUpCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "forky@1234", "복희", 26);
        SimpleRestAssured.post("/customers", customerRequest);
    }

    private String getTokenByLogin() {
        TokenRequest tokenRequest = new TokenRequest("forky", "forky@1234");
        return SimpleRestAssured.post("/login", tokenRequest)
                .toObject(TokenResponse.class)
                .getAccessToken();
    }

    private void assertCustomer(String accessToken, String userName, String password, String nickName, int age) {
        //given & when
        SimpleResponse response = SimpleRestAssured.getWithToken("/customers/me", accessToken);

        //then
        CustomerResponse customerResponse = response.toObject(CustomerResponse.class);
        assertAll(
                () -> response.assertStatus(HttpStatus.OK),
                () -> assertThat(customerResponse.getUsername()).isEqualTo(userName),
                () -> assertThat(customerResponse.getPassword()).isEqualTo(password),
                () -> assertThat(customerResponse.getNickname()).isEqualTo(nickName),
                () -> assertThat(customerResponse.getAge()).isEqualTo(age)
        );
    }

    private void assertInvalidCustomer(String accessToken) {
        SimpleResponse response = SimpleRestAssured.getWithToken("/customers/me", accessToken);
        response.assertStatus(HttpStatus.BAD_REQUEST);
        response.toObject(InvalidCustomerException.class);
    }
}
