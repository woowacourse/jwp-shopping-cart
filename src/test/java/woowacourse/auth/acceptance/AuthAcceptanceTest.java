package woowacourse.auth.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.acceptance.AcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "1234";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("회원 가입 성공")
    @Test
    void registerCustomers() {
        // when
        // 회원 정보를 담은 요청이 오면 회원가입에 성공하고 상태코드 201과 회원정보를 반환한다.
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/customers")
                .then().log().all()
                .extract();

        // then
        CustomerResponse customerResponse = registerCustomerResponse.body().as(CustomerResponse.class);
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo(NAME),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
        assertThat(registerCustomerResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());


    }


    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/customers")
                .then().log().all()
                .extract();

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = RestAssured
                .given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerResponse customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo(NAME),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    void myInfoWithBadBearerAuth() {
        // given
        // 회원이 등록되어 있고

        // when
        // 잘못된 id, password를 사용해 토큰을 요청하면

        // then
        // 토큰 발급 요청이 거부된다
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    void myInfoWithWrongBearerAuth() {
        // when
        // 유효하지 않은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보 조회 요청이 거부된다
    }
}
