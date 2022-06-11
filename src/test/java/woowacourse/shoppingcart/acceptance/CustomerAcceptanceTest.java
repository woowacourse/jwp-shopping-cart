package woowacourse.shoppingcart.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.auth.application.dto.TokenResponse;
import woowacourse.shoppingcart.application.dto.CustomerResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.ResponseCreator.*;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given & when
        ExtractableResponse<Response> response = postCustomers("basic@email.com", "password123@Q", "rookie");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        postCustomers("basic@email.com", "password123@Q", "rookie");
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("basic@email.com", "password123@Q");

        // when
        TokenResponse tokenResponse = 로그인_응답됨.as(TokenResponse.class);
        ExtractableResponse<Response> 사용자_정보_응답됨 = getCustomers(tokenResponse);

        // then
        CustomerResponse 사용자_정보 = 사용자_정보_응답됨.as(CustomerResponse.class);
        assertAll(
                () -> assertThat(사용자_정보_응답됨.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(사용자_정보).usingRecursiveComparison()
                        .isEqualTo(new CustomerResponse(1L, "basic@email.com", "rookie"))
        );
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        postCustomers("basic@email.com", "password123@Q", "rookie");
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("basic@email.com", "password123@Q");
        TokenResponse tokenResponse = 로그인_응답됨.as(TokenResponse.class);
        // when
        ExtractableResponse<Response> 닉네임_수정_응답됨 = patchCustomers(tokenResponse, "zero");
        ExtractableResponse<Response> 비밀번호_수정 = patchPasswordCustomers(tokenResponse, "password123@Q", "password123@A");
        // then
        ExtractableResponse<Response> 사용자_정보_응답됨 = getCustomers(tokenResponse);
        CustomerResponse 사용자_정보 = 사용자_정보_응답됨.as(CustomerResponse.class);
        assertAll(
                () -> assertThat(닉네임_수정_응답됨.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(비밀번호_수정.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(사용자_정보_응답됨.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(사용자_정보).usingRecursiveComparison()
                        .isEqualTo(new CustomerResponse(1L, "basic@email.com", "zero"))
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        postCustomers("basic@email.com", "password123@Q", "rookie");
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("basic@email.com", "password123@Q");
        TokenResponse tokenResponse = 로그인_응답됨.as(TokenResponse.class);

        // when
        ExtractableResponse<Response> 사용자_정보_삭제됨 = deleteCustomers(tokenResponse);

        // then
        assertThat(사용자_정보_삭제됨.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴 시 연관된 장바구니 내역도 삭제된다.")
    @Test
    void deleteMeAdditionalCart() {
        // given
        postCustomers("basic@email.com", "password123@Q", "rookie");
        ExtractableResponse<Response> 로그인_응답됨 = postLogin("basic@email.com", "password123@Q");
        TokenResponse tokenResponse = 로그인_응답됨.as(TokenResponse.class);
        postProduct("치킨", 20_000, "http://chicken.test.com");
        postCart(tokenResponse, 1L);

        // when
        ExtractableResponse<Response> 사용자_정보_삭제됨 = deleteCustomers(tokenResponse);

        // then
        assertThat(사용자_정보_삭제됨.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
