package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.ShoppingCartFixture.CUSTOMER_URI;
import static woowacourse.ShoppingCartFixture.잉_로그인요청;
import static woowacourse.ShoppingCartFixture.잉_비밀번호수정요청;
import static woowacourse.ShoppingCartFixture.잉_이름수정요청;
import static woowacourse.ShoppingCartFixture.잉_회원생성요청;
import static woowacourse.ShoppingCartFixture.잉_회원탈퇴요청;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.ExceptionResponse;

@DisplayName("회원 관련 기능")
@Sql("/truncate.sql")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("이름, 이메일, 비밀번호를 입력해서 회원 등록 요청한다")
    @Test
    void addCustomer() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;

        // when
        final ExtractableResponse<Response> 회원생성응답 = post(CUSTOMER_URI, 회원생성요청);

        // then
        assertThat(회원생성응답.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원 이름과 이메일을 확인할 수 있다")
    @Test
    void getMe() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);

        // when
        final String token = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 회원조회응답 = getWithToken(CUSTOMER_URI, token);
        final CustomerResponse 회원응답 = 회원조회응답.as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(회원조회응답.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(회원응답.getName()).isEqualTo(회원생성요청.getName()),
                () -> assertThat(회원응답.getEmail()).isEqualTo(회원생성요청.getEmail())
        );
    }

    @DisplayName("사용자 이름 수정")
    @Test
    void updateProfile() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);
        final CustomerUpdateProfileRequest 회원수정요청 = 잉_이름수정요청;

        // when
        final String token = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 회원수정응답 = put(CUSTOMER_URI + "/profile", 회원수정요청, token);
        final CustomerResponse 회원조회응답 = getWithToken(CUSTOMER_URI, token).as(CustomerResponse.class);

        // then
        assertAll(
                () -> assertThat(회원수정응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(회원조회응답.getName()).isEqualTo(회원수정요청.getName()),
                () -> assertThat(회원조회응답.getEmail()).isEqualTo(회원조회응답.getEmail())
        );
    }

    @DisplayName("사용자 비밀번호 수정")
    @Test
    void updatePassword() {
        // given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);
        final CustomerUpdatePasswordRequest 잉비밀번호수정요청 = 잉_비밀번호수정요청;

        // when
        final String token = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 회원수정응답 = put(CUSTOMER_URI + "/password", 잉비밀번호수정요청, token);
        final ExtractableResponse<Response> 비밀번호수정실패응답 = put(CUSTOMER_URI + "/password", 잉비밀번호수정요청, token);
        final ExceptionResponse 비밀번호수정실패 = 비밀번호수정실패응답.as(ExceptionResponse.class);

        // then
        assertAll(
                () -> assertThat(회원수정응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThat(비밀번호수정실패응답.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(비밀번호수정실패.getMessage()).contains("비밀번호가 일치하지 않습니다.")
        );
    }

    @DisplayName("토큰과 비밀번호 정보가 일치하면 회원탈퇴가 가능하다")
    @Test
    void deleteMe() {
        //given
        final CustomerRequest 회원생성요청 = 잉_회원생성요청;
        post(CUSTOMER_URI, 회원생성요청);
        final CustomerDeleteRequest 회원탈퇴요청 = 잉_회원탈퇴요청;

        //when
        final String token = getToken(잉_로그인요청);
        final ExtractableResponse<Response> 회원탈퇴응답 = deleteWithToken(CUSTOMER_URI, 회원탈퇴요청, token);

        //then
        assertAll(
                () -> assertThat(회원탈퇴응답.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value()),
                () -> assertThatCode(() -> getWithToken(CUSTOMER_URI, token).as(ExceptionResponse.class))
                        .doesNotThrowAnyException()
        );
    }
}
