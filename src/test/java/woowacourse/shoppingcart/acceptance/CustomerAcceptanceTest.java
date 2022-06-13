package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.AcceptanceFixture.createCustomer;
import static woowacourse.AcceptanceFixture.login;
import static woowacourse.Fixture.페퍼;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    /*
     *  Scenario: 회원 가입
     *   when: 회원 가입을 요청한다.
     *   then: 201 Created 상태 코드와 회원 정보를 응답받는다.
     */
    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        final Map<String, String> params = new HashMap<>();
        params.put("loginId", 페퍼_아이디);
        params.put("name", 페퍼_이름);
        params.put("password", 페퍼_비밀번호);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customers")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.body().jsonPath().getString("loginId")).isEqualTo(페퍼_아이디);
        assertThat(response.body().jsonPath().getString("name")).isEqualTo(페퍼_이름);
    }

    /*
     *  Scenario: 내 정보 조회
     *   given: 회원 가입된 회원이 존재하고, 로그인이 되어있다.
     *   when: 내 회원 정보를 요청한다.
     *   then: 200 OK 상태 코드와 회원 정보를 응답받는다.
     */
    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        //given
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        //when
        CustomerResponse customer = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/customers/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value()).extract().as(CustomerResponse.class);

        //then
        Assertions.assertAll(
                () -> assertThat(customer.getLoginId()).isEqualTo(페퍼_아이디),
                () -> assertThat(customer.getName()).isEqualTo(페퍼_이름)
        );
    }

    /*
     *  Scenario: 내 정보 조회
     *   given: 회원 가입된 회원이 존재하고, 로그인이 되어있다.
     *   when: 내 회원 정보를 요청한다.
     *   then: 200 OK 상태 코드와 회원 정보를 응답받는다.
     */
    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        //given
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        //when
        CustomerResponse updateResponse = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(new CustomerUpdateRequest("newName", 페퍼_비밀번호))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/customers/me")
                .then().log().all()
                .extract().as(CustomerResponse.class);

        //then
        Assertions.assertAll(
                () -> assertThat(updateResponse.getLoginId()).isEqualTo(페퍼_아이디),
                () -> assertThat(updateResponse.getName()).isEqualTo("newName")
        );
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
        //given
        createCustomer(페퍼);
        ExtractableResponse<Response> login = login(페퍼_아이디, 페퍼_비밀번호);
        String accessToken = login.as(TokenResponse.class).getAccessToken();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .body(new CustomerDeleteRequest(페퍼_비밀번호))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/customers/me")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
