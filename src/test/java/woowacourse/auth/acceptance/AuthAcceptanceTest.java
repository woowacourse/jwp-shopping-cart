package woowacourse.auth.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.auth.dto.TokenResponse;

@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String HUDI_GMAIL_COM = "hudi@gmail.com";
    private static final String HUDI_PASSWORD = "a1@12345";

    @DisplayName("Bearer Auth 로그인 성공")
    @Test
    void myInfoWithBearerAuth() {
        // given
        // 회원이 등록되어 있고
        // id, password를 사용해 토큰을 발급받고

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면

        // then
        // 내 정보가 조회된다
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

    @DisplayName("로그인 성공 시 토큰을 발급한다.")
    @Test
    void login() {
        //given
        createUser();

        // when
        Map<String, String> params = new HashMap<>();
        params.put("email", HUDI_GMAIL_COM);
        params.put("password", HUDI_PASSWORD);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customer/authentication/sign-in")
                .then().log().all()
                .extract();

        TokenResponse tokenResponse = response.jsonPath().getObject(".", TokenResponse.class);

        //then
        assertAll(
                () -> assertThat(tokenResponse.getAccessToken()).isNotBlank(),
                () -> assertThat(tokenResponse.getCustomerId()).isPositive()
        );
    }

    private void createUser() {
        Map<String, Object> params = new HashMap<>();
        params.put("email", HUDI_GMAIL_COM);
        params.put("password", HUDI_PASSWORD);
        params.put("profileImageUrl", "http://gravatar.com/avatar/1?d=identicon");
        params.put("name", "조동현");
        params.put("gender", "male");
        params.put("birthDay", "1998-12-21");
        params.put("contact", "01074415409");
        params.put("fullAddress", Map.of("address", "서울특별시 강남구 선릉역", "detailAddress", "이디야 책상", "zoneCode", "12345"));
        params.put("terms", true);

        RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }
}
