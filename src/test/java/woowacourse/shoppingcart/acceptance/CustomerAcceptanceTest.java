package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.ExceptionResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.CustomerNameResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("이메일 중복 확인할 때, 중복인 경우 예외 메세지를 반환 반환한다.")
    @Test
    void isDuplicationEmail() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");
        saveCustomerApi(customerRequest);

        // when
        String email = "email";
        ExceptionResponse response = RestAssured.given().log().all()
                .param("email", email)
                .when()
                .post("/customers/email/validate")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ExceptionResponse.class);

        //then
        assertThat(response.getMessage()).isEqualTo("중복된 email 입니다.");
    }

    @DisplayName("이메일 중복 확인할 때, 중복이 아닌 경우 예외가 발생하지 않는다.")
    @Test
    void isNotDuplicationEmail() {
        //then
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .param("email", "email")
                .when()
                .post("/customers/email/validate")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");

        // when
        ExtractableResponse<Response> response = saveCustomerApi(customerRequest);
        CustomerResponse customerResponse = response.jsonPath().getObject(".", CustomerResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly("email", "name", "010-1234-5678", "address");
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");
        saveCustomerApi(customerRequest);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        String accessToken = getAccessToken(tokenRequest);
        //when
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(new CustomerRequest("email", "Pw123456!", "judy", "010-1111-2222", "address2"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
        //then
        CustomerResponse customerResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerResponse.class);

        assertThat(customerResponse).extracting("email", "name", "phone", "address")
                .containsExactly("email", "judy", "010-1111-2222", "address2");
    }

    @DisplayName("회원 이름 조회")
    @Test
    void findName() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");
        saveCustomerApi(customerRequest);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        String accessToken = getAccessToken(tokenRequest);

        //then
        CustomerNameResponse name = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/customers/me/name")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CustomerNameResponse.class);

        assertThat(name.getName()).isEqualTo("name");
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("email", "Pw123456!", "name", "010-1234-5678", "address");
        saveCustomerApi(customerRequest);

        TokenRequest tokenRequest = new TokenRequest("email", "Pw123456!");
        String accessToken = getAccessToken(tokenRequest);
        //when
        RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
        //then
        ExceptionResponse response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ExceptionResponse.class);

        assertThat(response.getMessage()).isEqualTo("존재하지 않는 회원입니다.");
    }

    public static ExtractableResponse<Response> saveCustomerApi(CustomerRequest customerRequest) {
        return RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();
    }

    private String getAccessToken(TokenRequest tokenRequest) {
        return RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/auth/login")
                .then().log().all()
                .extract().as(TokenResponse.class).getAccessToken();
    }
}
