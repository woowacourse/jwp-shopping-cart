package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.BAD_REQUEST;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.NOT_FOUND;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.NO_CONTENT;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.OK;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.UNAUTHORIZED;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.나의_정보조회;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.내_정보_수정;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.로그인;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.비밀번호_변경;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.예외메세지_검증;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.회원가입;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.회원탈퇴;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.PasswordChangeRequest;

@DisplayName("회원 관련 인수테스트")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입을_한다_AND_로그인을_한다_AND_내_정보를_조회한다() {
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
        // 가입된 회원 정보를 조회한다.
        ExtractableResponse<Response> response = 나의_정보조회(accessToken);
        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        assertAll(
                () -> OK(response),
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
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // when then
        // 동일한 username 으로 회원가입을 시도하면 예외를 발생시킨다.
        CustomerRequest newCustomerRequest = new CustomerRequest("jo@naver.com", "jojored", "abcd1234!");
        ExtractableResponse<Response> response = 회원가입(newCustomerRequest);
        assertAll(
                () -> BAD_REQUEST(response),
                () -> 예외메세지_검증(response, "중복된 값이 존재합니다.")
        );
    }

    @Test
    void nickname_이_이미_존재하면_회원가입을_할_수_없다() {
        // given
        // 가입된 회원이 존재한다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // when then
        // 동일한 nickname 으로 회원가입을 시도하면 예외를 발생시킨다.
        CustomerRequest newCustomerRequest = new CustomerRequest("hunch@naver.com", "jojogreen", "abcd1234!");
        ExtractableResponse<Response> response = 회원가입(newCustomerRequest);
        assertAll(
                () -> BAD_REQUEST(response),
                () -> 예외메세지_검증(response, "중복된 값이 존재합니다.")
        );
    }

    @Test
    void 부적절한_회원_정보로는_회원가입을_할_수_없다() {
        // given
        // 잘못된 회원 정보가 존재한다.
        CustomerRequest customerRequest = new CustomerRequest("jo@navercom", "?", "1234");

        // when then
        // 회원가입을 시도하면 예외를 발생시킨다.
        BAD_REQUEST(회원가입(customerRequest));
    }

    @Test
    void 존재하지_않는_회원은_로그인_할_수_없다() {
        // given when
        // 회원가입되지 않은 username 으로 로그인을 한다.
        ExtractableResponse<Response> response = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"));

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> UNAUTHORIZED(response),
                () -> 예외메세지_검증(response, "아이디 또는 비밀번호를 확인하여주세요.")
        );
    }

    @Test
    void 잘못된_비밀번호로는_로그인을_할_수_없다() {
        // given
        // 회원가입을 한다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // when
        // 잘못된 비밀번호로 로그인을 시도한다.
        ExtractableResponse<Response> response = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd@"));

        // then
        // 가입된 회원 정보를 조회된다.
        assertAll(
                () -> UNAUTHORIZED(response),
                () -> 예외메세지_검증(response, "아이디 또는 비밀번호를 확인하여주세요.")
        );
    }

    @Test
    void 회원탈퇴를_할_수_있다_AND_회원탈퇴를_했을_경우_내_정보를_조회할_수_없다() {
        // given
        // 회원가입을 한다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // 로그인을 하여 토큰을 발급받는다.
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 회원 탈퇴를 한다.
        ExtractableResponse<Response> 회원탈퇴Response = 회원탈퇴(accessToken);
        NO_CONTENT(회원탈퇴Response);

        // then
        // 탈퇴한 회원 정보를 조회하면 예외를 발생시킨다.
        ExtractableResponse<Response> 나의_정보조회Response = 나의_정보조회(accessToken);
        assertAll(
                () -> NOT_FOUND(나의_정보조회Response),
                () -> 예외메세지_검증(나의_정보조회Response, "존재하지 않는 회원입니다.")
        );
    }

    @Test
    void 이미_탈퇴한_회원은_탈퇴할_수_없다() {
        // given
        // 회원가입을 한다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // 로그인을 하여 토큰을 발급받는다.
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // 회원탈퇴를 한다.
        회원탈퇴(accessToken);

        // when
        // 이미 탈퇴한 회원의 토큰으로 다시 탈퇴한다.
        ExtractableResponse<Response> response = 회원탈퇴(accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> NOT_FOUND(response),
                () -> 예외메세지_검증(response, "존재하지 않는 회원입니다.")
        );
    }

    @Test
    void 내_정보를_수정한다() {
        // given
        // 회원가입을 한다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));

        // 로그인을 하여 토큰을 발급받는다.
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 내 정보를 수정한다.
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("hunch");
        내_정보_수정(customerUpdateRequest, accessToken);

        // then
        // 예외를 발생시킨다.
        ExtractableResponse<Response> response = 나의_정보조회(accessToken);
        CustomerResponse customerResponse = response.as(CustomerResponse.class);
        assertAll(
                () -> OK(response),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerUpdateRequest.getNickname())
        );
    }

    @Test
    void 존재하지_않는_회원의_정보를_수정할_수_없다() {
        // given
        // 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // 회원 탈퇴를 한다.
        회원탈퇴(accessToken);

        // when
        // 내 정보를 수정한다.
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("hunch");
        ExtractableResponse<Response> response = 내_정보_수정(customerUpdateRequest, accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> NOT_FOUND(response),
                () -> 예외메세지_검증(response, "존재하지 않는 회원입니다.")
        );
    }

    @Test
    void 이미_존재하는_nickname_으로는_수정할_수_없다() {
        // given
        // 기존에 회원이 존재한다.
        회원가입(new CustomerRequest("hunch@naver.com", "hunch", "1234abcd@"));

        // 새로운 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 이미 존재하는 nickname 으로 내 정보를 수정한다.
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("hunch");
        ExtractableResponse<Response> response = 내_정보_수정(customerUpdateRequest, accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> BAD_REQUEST(response),
                () -> 예외메세지_검증(response, "중복된 값이 존재합니다.")
        );
    }

    @Test
    void 비밀번호를_변경할_수_있다() {
        // given
        // 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 비밀번호를 변경한다.
        비밀번호_변경(new PasswordChangeRequest("1234abcd!", "1234abcd@"), accessToken);

        // then
        // 변경된 비밀번호로 로그인을 한다.
        OK(로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd@")));
    }

    @Test
    void 기존_비밀번호가_일치하지_않는_경우_변경할_수_없다() {
        // given
        // 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 기존 비밀번호가 일치하지 않는 상황에서 비밀번호를 변경한다.
        ExtractableResponse<Response> response = 비밀번호_변경(new PasswordChangeRequest("1234abcd$", "1234abcd@"), accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> BAD_REQUEST(response),
                () -> 예외메세지_검증(response, "비밀번호가 일치하지 않습니다.")
        );
    }

    @Test
    void 잘못된_비밀번호_형식으로는_변경할_수_없다() {
        // given
        // 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 잘못된 비밀번호 형식으로 비밀번호를 변경한다.
        ExtractableResponse<Response> response = 비밀번호_변경(new PasswordChangeRequest("1234abcd!", "1234"), accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> BAD_REQUEST(response),
                () -> 예외메세지_검증(response, "올바르지 않은 포맷의 패스워드 입니다.")
        );
    }

    @Test
    void 존재하지_않는_회원의_비밀번호를_변경할_수_없다() {
        // given
        // 회원가입을 하고 로그인을 하여 토큰을 발급받는다.
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "1234abcd!"));
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "1234abcd!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // 회원 탈퇴를 한다.
        회원탈퇴(accessToken);

        // when
        // 존재하지 않는 회원의 비밀번호를 변경한다.
        ExtractableResponse<Response> response = 비밀번호_변경(new PasswordChangeRequest("1234abcd!", "1234abcd@"), accessToken);

        // then
        // 예외를 발생시킨다.
        assertAll(
                () -> NOT_FOUND(response),
                () -> 예외메세지_검증(response, "존재하지 않는 회원입니다.")
        );
    }
}
