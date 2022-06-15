package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.RequestFixture.*;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    /*
        Scenario: 회원 가입
            When: 회원 가입을 요청한다.
            Then: 201 상태, Location 헤더를 응답한다.
    */
    @Test
    void 회원_가입() {
        // when
        ExtractableResponse<Response> createResponse = 회원_가입_요청("ellie", "Ellie1234!");

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isEqualTo("/api/customers/ellie");
    }

    /*
        Scenario: 중복된 이름으로 회원 가입
            Given: 회원 가입을 한다.
            When: 같은 이름으로 회원 가입을 요청한다.
            Then: 400 상태, 에러 메시지를 응답한다.
    */
    @Test
    void 중복된_이름으로_회원_가입() {
        // given
        회원_가입_요청("ellie", "Ellie1234!");

        // when
        ExtractableResponse<Response> createResponse = 회원_가입_요청("ellie", "Ellie1234!");

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /*
        Scenario: 누란된 정보로 회원 가입
            When: 누란된 정보가 있는 상테에서 회원 가입을 요청한다.
            Then: 400 상태, 에러 메시지를 응답한다.
    */
    @Test
    void 회원가입_시_누락된_필드값_존재() {
        // when
        ExtractableResponse<Response> createResponse = 회원_가입_요청("ellie", null);

        // given
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /*
        Scenario: 내 정보 조회
            Given: 회원 가입을 한다.
            And: 로그인을 해 토큰을 발급받는다.
            When: 내 정보 조회를 요청한다.
            Then: 200 상태, 회원 정보를 응답한다.
    */
    @Test
    void 내_정보_조회() {
        // given
        회원_가입_요청("ellie", "Ellie1234!");
        String accessToken = 로그인_및_토큰_발급("ellie", "Ellie1234!");

        // when
        ExtractableResponse<Response> getResponse = 내_정보_조회_요청(accessToken);

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.body().jsonPath().getString("userName")).isEqualTo("ellie");
    }

    /*
        Scenario: 유효하지 않은 토큰으로 내 정보 조회
            When: 토큰 없이 내 정보 조회를 요청한다.
            Then: 401 상태, 에러 메시지를 응답한다.
    */
    @Test
    void 유효하지_않은_토큰으로_내_정보_조회() {
        // when
        ExtractableResponse<Response> getResponse = 내_정보_조회_요청("invalid_token");

        // then
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    /*
        Scenario: 내 정보 수정
            Given: 회원 가입을 한다.
            And: 로그인을 해 토큰을 발급받는다.
            When: 내 정보 수정을 요청한다.
            Then: 200 상태를 응답한다.
    */
    @Test
    void 내_정보_수정() {
        // given
        회원_가입_요청("ellie", "Ellie1234!");
        String accessToken = 로그인_및_토큰_발급("ellie", "Ellie1234!");

        // when
        ExtractableResponse<Response> editResponse = 내_정보_수정_요청(accessToken, "ellie", "Ellie1234@");

        // then
        ExtractableResponse<Response> loginResponse = 로그인("ellie", "Ellie1234@");

        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    /*
    Scenario: 유효하지 않은 토큰으로 내 정보 수정
        When: 토큰 없이 내 정보 수정을 요청한다.
        Then: 401 상태, 에러 메시지를 응답한다.
    */
    @Test
    void 유효하지_않은_토큰으로_내_정보_수정() {
        // when
        ExtractableResponse<Response> editResponse = 내_정보_수정_요청("invalid_token", "ellie", "123456789");

        // then
        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    /*
        Scenario: 회원 탈퇴
            Given: 회원 가입을 한다.
            And: 로그인을 해 토큰을 발급받는다.
            When: 회원 탈퇴를 요청한다.
            Then: 204 상태를 응답한다.
    */
    @Test
    void 회원_탈퇴() {
        // given
        회원_가입_요청("ellie", "Ellie1234!");
        String accessToken = 로그인_및_토큰_발급("ellie", "Ellie1234!");

        // when
        ExtractableResponse<Response> deleteResponse = 회원_탈퇴_요청(accessToken);

        // then
        ExtractableResponse<Response> getResponse = 내_정보_조회_요청(accessToken);

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    /*
        Scenario: 장바구니에 아이템이 담겨 있는 상태에서 회원 탈퇴
            Given: 상품이 등록되어 있다.
            And: 회원 가입을 한다.
            And: 로그인을 한다.
            And: 장바구니에 상품을 담는다.
            When: 회원 탈퇴를 요청한다.
            Then: 204 상태를 응답한다.
    */
    @Test
    void 장바구니에_아이템이_담겨_있는_상태로_회원_탈퇴() {
        // given
        Long productId = 상품_등록되어_있음("MacBook Air", 1_400_000,
                "https://www.apple.com/v/macbook-air-m2/a/images/overview/compare/compare_mba__bjfeags91jyu_large_2x.png");
        회원_가입_요청("ellie", "Ellie1234!");
        String accessToken = 로그인_및_토큰_발급("ellie", "Ellie1234!");
        장바구니_아이템_추가_요청(accessToken, productId);

        // when
        ExtractableResponse<Response> deleteResponse = 회원_탈퇴_요청(accessToken);

        // then
        ExtractableResponse<Response> getResponse = 내_정보_조회_요청(accessToken);

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(getResponse.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    /*
       Scenario: 유효하지 않은 토큰으로 회원 탈퇴
           When: 토큰 없이 회원 탈퇴를 요청한다.
           Then: 401 상태, 에러 메시지를 응답한다.
    */
    @Test
    void 유효하지_않은_토큰으로_회원_탈퇴() {
        // when
        ExtractableResponse<Response> deleteResponse = 회원_탈퇴_요청("invalid_token");

        // then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    /*
       Scenario: 존재하는 이름으로 회원 이름 중복 검사
           Given: 회원 가입을 한다.
           When: 회원 가입 했던 이름으로 이름 중복 검사를 요청한다.
           Then: 200 상태, 있음(True)를 반환한다.
    */
    @Test
    void 존재하는_이름으로_회원_이름_중복_검사() {
        // given
        회원_가입_요청("ellie", "Ellie1234!");

        // when
        ExtractableResponse<Response> checkDuplicationResponse = 회원_이름_중복_검사_요청("ellie");

        // then
        assertAll(
                () -> assertThat(checkDuplicationResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(checkDuplicationResponse.body().jsonPath().getBoolean("isDuplicate")).isTrue()
        );
    }

    /*
       Scenario: 존재하지 않는 이름으로 회원 이름 중복 검사
           When: 이름 중복 검사를 요청한다.
           Then: 200 상태, 없음(False)를 반환한다.
    */
    @Test
    void 존재하지_않는_이름으로_회원_이름_중복_검사() {
        // when
        ExtractableResponse<Response> checkDuplicationResponse = 회원_이름_중복_검사_요청("ellie");

        // then
        assertAll(
                () -> assertThat(checkDuplicationResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(checkDuplicationResponse.body().jsonPath().getBoolean("isDuplicate")).isFalse()
        );
    }
}
