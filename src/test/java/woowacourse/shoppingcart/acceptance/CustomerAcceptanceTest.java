package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.나의_정보조회;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.로그인;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.회원가입;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("회원 관련 인수테스트")
@Sql("/init.sql")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입을_한다_AND_로그인을_한다() {
        // given
        // 가입하고자 하는 회원 정보가 존재한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!");

        // when
        // 회원가입을 한다.
        회원가입(customerRequest);

        // 로그인을 한다.
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // then
        // 가입된 회원 정보가 조회된다.
        CustomerResponse customerResponse = 나의_정보조회(accessToken).as(CustomerResponse.class);
        assertAll(
                () -> assertThat(customerResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }

    @Test
    void username_이_이미_존재하면_회원가입을_할_수_없다() {
        // given
        // 가입된 회원이 존재한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!");
        회원가입(customerRequest);

        // when then
        // 동일한 username 으로 회원가입을 시도하면 예외를 발생시킨다.
        CustomerRequest newCustomerRequest = new CustomerRequest("jo@naver.com", "jojored", "abcd1234!");
        ExtractableResponse<Response> response = 회원가입(newCustomerRequest);
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().asString())
                        .isEqualTo("중복된 값이 존재합니다.")
        );
    }

    @Test
    void nickname_이_이미_존재하면_회원가입을_할_수_없다() {
        // given
        // 가입된 회원이 존재한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!");
        회원가입(customerRequest);

        // when then
        // 동일한 nickname 으로 회원가입을 시도하면 예외를 발생시킨다.
        CustomerRequest newCustomerRequest = new CustomerRequest("hunch@naver.com", "jojogreen", "abcd1234!");
        ExtractableResponse<Response> response = 회원가입(newCustomerRequest);
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(response.body().asString())
                        .isEqualTo("중복된 값이 존재합니다.")
        );
    }

    @Test
    void 부적절한_회원_정보로는_회원가입을_할_수_없다() {
        // given
        // 잘못된 회원 정보가 존재한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@navercom", "?", "1234");

        // when then
        // 회원가입을 시도하면 예외를 발생시킨다.
        ExtractableResponse<Response> response = 회원가입(customerRequest);
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 존재하지_않는_회원은_로그인_할_수_없다() {
        // given when
        // 회원가입되지 않은 username 으로 로그인을 한다.
        ExtractableResponse<Response> response = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"));

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.body().asString())
                        .isEqualTo("로그인 할 수 없습니다.")
        );
    }

    @Test
    void 잘못된_비밀번호로는_로그인을_할_수_없다() {
        // given
        // 회원가입을 한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!");
        회원가입(customerRequest);

        // when
        // 잘못된 비밀번호로 로그인을 시도한다.
        ExtractableResponse<Response> response = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd@"));

        // then
        // 가입된 회원 정보가 조회된다.
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.UNAUTHORIZED.value()),
                () -> assertThat(response.body().asString())
                        .isEqualTo("로그인 할 수 없습니다.")
        );
    }
}
