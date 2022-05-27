package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
        String loginId = "pepper@woowacourse.com";
        String userName = "pepper";
        String password = "Qwer1234!";

        final Map<String, String> params = new HashMap<>();
        params.put("loginId", loginId);
        params.put("userName", userName);
        params.put("password", password);

        // when
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/customer")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.body().jsonPath().getString("loginId")).isEqualTo(loginId);
        assertThat(response.body().jsonPath().getString("userName")).isEqualTo(userName);
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
