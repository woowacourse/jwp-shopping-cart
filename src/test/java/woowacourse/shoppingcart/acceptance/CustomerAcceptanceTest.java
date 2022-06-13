package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.ChangeCustomerRequest;
import woowacourse.shoppingcart.dto.ChangePasswordRequest;
import woowacourse.shoppingcart.dto.CreateCustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일, 패스워드, 닉네임을 입력 받아 회원가입을 한다.")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> response = sendCreateCustomerRequest(
                new CreateCustomerRequest("beomWhale1@naver.com", "범고래1", "Password12345!"));

        assertAll(
                () -> assertThat(response.header("Location")).isNotEmpty(),
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @DisplayName("회원가입 시 이메일이 중복될 경우, 예외 응답을 반환한다.")
    @Test
    void errorResponseWhenDuplicateEmail() {
        // given: 회원이 등록되어 있다.
        String email = "beomWhale1@naver.com";
        createCustomer(new CreateCustomerRequest(email, "범고래1", "Password12345!"));

        // when: 이미 등록되어 있는 이메일로 회원 생성을 시도하면
        ExtractableResponse<Response> response = createCustomer(
                new CreateCustomerRequest(email, "범고래2", "Password12345!"));

        // then: 예외 응답을 반환한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "이미 존재하는 이메일입니다.")
        );
    }

    @DisplayName("회원가입 시 닉네임이 중복될 경우, 예외 응답을 반환한다.")
    @Test
    void duplicateCustomerNickname() {
        // given: 회원이 등록되어 있다.
        String nickname = "범고래1";
        createCustomer(new CreateCustomerRequest("beomWhale1@naver.com", nickname, "Password12345!"));

        // when: 이미 등록되어 있는 닉네임으로 회원 생성을 시도하면
        ExtractableResponse<Response> response = createCustomer(
                new CreateCustomerRequest("beomWhale2@naver.com", nickname, "Password12345!"));

        // then: 예외 응답을 반환한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo(
                        "이미 존재하는 닉네임입니다.")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given: 등록된 회원으로 로그인한다.
        String email = "beomWhale1@naver.com";
        String password = "Password12345!";
        createCustomer(new CreateCustomerRequest(email, "범고래1", password));

        String accessToken = 로그인_요청(new TokenRequest(email, password));

        // when: 회원삭제 요청후 로그인하면
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when().log().all()
                .delete("/api/customers")
                .then().extract();

        ExtractableResponse<Response> loginResponse = sendLoginRequest(email, password);

        // then: 예외응답을 반환한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(loginResponse.body().jsonPath().getString("message")).isEqualTo(
                        "존재하지 않는 회원입니다.")
        );
    }

    @DisplayName("이전 패스워드와 새로운 패스워드를 입력받아 새로운 패스워드로 변경한다.")
    @Test
    void changePassword() {
        // given: 등록된 회원으로 로그인한다.
        String email = "beomWhale1@naver.com";
        String password = "Password12345!";
        createCustomer(new CreateCustomerRequest(email, "범고래1", password));
        String accessToken = 로그인_요청(new TokenRequest(email, password));

        // when: 패스워드 변경 요청을 보내고, 변경된 패스워드로 로그인하면
        String newPassword = "Password123456!";
        ExtractableResponse<Response> response = changePasswordRequest(accessToken, password, newPassword);
        ExtractableResponse<Response> loginResponse = sendLoginRequest(email, newPassword);

        // then: 로그인에 성공한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value())
        );
    }

    @DisplayName("새로운 닉네임으로 회원정보를 수정한다.")
    @Test
    void changeNickname() {
        // given: 등록된 회원으로 로그인한다.
        String email = "beomWhale1@naver.com";
        String password = "Password12345!";
        createCustomer(new CreateCustomerRequest(email, "범고래1", password));
        String accessToken = 로그인_요청(new TokenRequest(email, password));

        // when: 닉네임 변경 요청을 보내고 회원정보를 조회하면
        String newNickname = "changed";
        ExtractableResponse<Response> response = changeNicknameRequest(newNickname, accessToken);

        CustomerResponse customerResponse = RestAssured.given()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/customers/me")
                .then()
                .extract().as(CustomerResponse.class);

        // then: 닉네임 변경이 반영된 회원정보 응답을 받는다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(customerResponse.getNickname()).isEqualTo(newNickname)
        );
    }

    @DisplayName("닉네임 변경시 기존에 존재하는 닉네임과 중복되면 예외를 응답한다.")
    @Test
    void throwExceptionWhenDuplicateNickname() {
        // given: 회원이 등록되어 있다.
        String email = "beomWhale2@naver.com";
        String nickname = "범고래1";
        String password = "Password12345!";
        createCustomer(new CreateCustomerRequest("beomWhale1@naver.com", nickname, "Password12345!"));
        createCustomer(new CreateCustomerRequest(email, "범고래2", password));

        String accessToken = 로그인_요청(new TokenRequest(email, password));

        // when: 기존에 존재하는 닉네임으로 닉네임을 변경하면
        ExtractableResponse<Response> response = changeNicknameRequest(nickname, accessToken);

        // then: 닉네임 변경에 실패한다.
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().jsonPath().getString("message")).isEqualTo("이미 존재하는 닉네임입니다.")
        );
    }

    private ExtractableResponse<Response> changeNicknameRequest(String newNickname, String accessToken) {
        ChangeCustomerRequest changeCustomerRequest = new ChangeCustomerRequest(newNickname);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(changeCustomerRequest)
                .when().log().all()
                .patch("/api/customers")
                .then().log().all()
                .extract();
        return response;
    }

    private ExtractableResponse<Response> changePasswordRequest(String accessToken, String password, String newPassword) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(new ChangePasswordRequest(password, newPassword))
                .when()
                .patch("/api/customers/password")
                .then().extract();
    }

    private ExtractableResponse<Response> sendCreateCustomerRequest(CreateCustomerRequest customerCreateRequest) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerCreateRequest)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }
}
