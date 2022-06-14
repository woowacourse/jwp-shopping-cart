package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.acceptance.AuthAcceptanceTest;
import woowacourse.auth.dto.*;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.EmailRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "leo@woowahan.com";
    private static final String PASSWORD = "Bunny1234!@";
    private static final String NAME = "leo";
    private static final String PHONE = "010-1234-5678";
    private static final String ADDRESS = "Seoul";

    @DisplayName("회원 가입 성공")
    @Test
    void registerCustomers() {
        // when
        // 회원 정보를 담은 요청이 오면
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        ExtractableResponse<Response> registerCustomerResponse = 회원가입(customerRequest);

        // then
        // 회원가입에 성공하고 상태코드 201과 회원정보를 반환한다.
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

    @DisplayName("회원 정보 수정")
    @Test
    void editCustomerInformation() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = AuthAcceptanceTest.로그인(tokenRequest);

        // when
        // 회원 정보를 수정하고 내 정보 조회를 요청하면
        CustomerRequest updateCustomerRequest = new CustomerRequest(EMAIL, PASSWORD, "bani", PHONE, ADDRESS);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .body(updateCustomerRequest)
                .when().put("/customers")
                .then().log().all()
                .extract();

        CustomerResponse customerResponse = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        // then
        // 수정된 내 정보가 조회된다.
        assertAll(
                () -> assertThat(customerResponse.getId()).isEqualTo(1L),
                () -> assertThat(customerResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerResponse.getName()).isEqualTo("bani"),
                () -> assertThat(customerResponse.getPhone()).isEqualTo(PHONE),
                () -> assertThat(customerResponse.getAddress()).isEqualTo(ADDRESS)
        );
    }

    @DisplayName("회원 정보 삭제")
    @Test
    void deleteCustomer() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        TokenRequest tokenRequest = new TokenRequest(EMAIL, PASSWORD);
        String accessToken = AuthAcceptanceTest.로그인(tokenRequest);

        // when
        // 패스워드와 함께 회원 삭제를 요청하면
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .auth().oauth2(accessToken)
                .when().delete("/customers")
                .then().log().all()
                .extract();

        // then
        // 204 상태코드를 반환한다.
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("이메일 중복 확인 요청")
    @Test
    void checkEmailDuplication() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest(EMAIL, PASSWORD, NAME, PHONE, ADDRESS);
        회원가입(customerRequest);

        // when
        // 이메일 중복을 확인하면
        EmailRequest emailRequest = new EmailRequest(EMAIL);

        ExtractableResponse<Response> validEmailResponse = RestAssured
                .given().log().all()
                .body(emailRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/email/validate")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract();

        // then
        //  사용 불가능한 이메일인지 확인한다.
        assertThat(validEmailResponse.jsonPath().getBoolean("isValidEmail")).isFalse();
    }

    public static ExtractableResponse<Response> 회원가입(CustomerRequest customerRequest) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .when().post("/customers")
                .then().log().all()
                .extract();
    }
}
