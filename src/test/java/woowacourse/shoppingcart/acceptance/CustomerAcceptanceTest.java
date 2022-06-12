package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.auth.support.AuthorizationExtractor.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.acceptance.fixture.CustomerAcceptanceFixture;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.ui.dto.EmailDuplicationRequest;
import woowacourse.shoppingcart.ui.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UserNameDuplicationRequest;
import woowacourse.support.SimpleRestAssured;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Autowired
    JwtTokenProvider tokenProvider;

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        ExtractableResponse<Response> saveResponse = CustomerAcceptanceFixture.saveCustomer();

        assertAll(
            () -> assertThat(saveResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(saveResponse.header("Location")).startsWith("/api/customers")
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        final Long customerId = SimpleRestAssured.getId(CustomerAcceptanceFixture.saveCustomer());
        String token = BEARER_TYPE + tokenProvider.createToken(customerId.toString());

        final ExtractableResponse<Response> response =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));
        CustomerResponse customerResponse = SimpleRestAssured.toObject(response, CustomerResponse.class);

        Assertions.assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(customerResponse.getUsername()).isEqualTo("username")
        );

    }

    @DisplayName("존재하지 않는 회원을 조회하면 예외를 발생한다.")
    @Test
    void notFoundException() {
        String token = BEARER_TYPE + tokenProvider.createToken("999");

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));

        assertThat(foundResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        final Long id = SimpleRestAssured.getId(CustomerAcceptanceFixture.saveCustomer());

        String token = BEARER_TYPE + tokenProvider.createToken(id.toString());
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest("another-address", "010-9999-9998");
        ExtractableResponse<Response> updatedResponse =
            SimpleRestAssured.put("/api/customers/me", new Header("Authorization", token), updateCustomerRequest);

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));
        CustomerResponse customerResponse = SimpleRestAssured.toObject(foundResponse, CustomerResponse.class);

        assertAll(
            () -> assertThat(updatedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
            () -> assertThat(customerResponse.getAddress()).isEqualTo("another-address"),
            () -> assertThat(customerResponse.getPhoneNumber()).isEqualTo("010-9999-9998")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        final Long id = SimpleRestAssured.getId(CustomerAcceptanceFixture.saveCustomer());

        String token = BEARER_TYPE + tokenProvider.createToken(id.toString());
        ExtractableResponse<Response> deletedResponse =
            SimpleRestAssured.delete("/api/customers/me", new Header("Authorization", token));

        ExtractableResponse<Response> foundResponse =
            SimpleRestAssured.get("/api/customers/me", new Header("Authorization", token));

        assertThat(deletedResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(foundResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("회원이름이 중복인지 확인한다")
    @Test
    void isUserNameDuplicated() {
        // given
        final String username = "duplication";
        CustomerAcceptanceFixture.saveCustomerWithName(username);

        // when
        final ExtractableResponse<Response> response =
            SimpleRestAssured.post(
                "/api/customers/duplication/username", new UserNameDuplicationRequest(username)
            );
        final UserNameDuplicationResponse duplicationResponse = SimpleRestAssured.toObject(response,
            UserNameDuplicationResponse.class);

        // then
        Assertions.assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(duplicationResponse.getUsername()).isEqualTo(username),
            () -> assertThat(duplicationResponse.getDuplicated()).isTrue()
        );
    }

    @DisplayName("이메일이 중복인지 확인한다")
    @Test
    void isEmailDuplicated() {
        // given
        final String email = "duplication@email.com";
        CustomerAcceptanceFixture.saveCustomerWithEmail(email);

        // when
        final ExtractableResponse<Response> response =
            SimpleRestAssured.post(
                "/api/customers/duplication/email", new EmailDuplicationRequest(email)
            );
        final EmailDuplicationResponse duplicationResponse = SimpleRestAssured.toObject(response,
            EmailDuplicationResponse.class);

        // then
        Assertions.assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(duplicationResponse.getEmail()).isEqualTo(email),
            () -> assertThat(duplicationResponse.getDuplicated()).isTrue()
        );

    }
}
