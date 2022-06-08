package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.나의_정보조회;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.로그인;
import static woowacourse.shoppingcart.acceptance.AcceptanceFixtures.회원가입;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerLoginRequest;
import woowacourse.shoppingcart.dto.CustomerLoginResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.ExceptionRequest;

@DisplayName("인증 관련 인수테스트")
@Sql("/init.sql")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 정상적으로_토큰이_발급된다() {
        // given
        // 회원이 등록되어 있고
        CustomerRequest customerRequest = new CustomerRequest("jo@naver.com", "jojogreen", "abcde123!");
        회원가입(new CustomerRequest("jo@naver.com", "jojogreen", "abcde123!"));

        // id, password 를 사용해 로그인하여 토큰을 발급받고
        String accessToken = 로그인(new CustomerLoginRequest("jo@naver.com", "abcde123!"))
                .as(CustomerLoginResponse.class)
                .getAccessToken();

        // when
        // 발급 받은 토큰을 사용하여 내 정보 조회를 요청하면
        CustomerResponse customerResponse = 나의_정보조회(accessToken).as(CustomerResponse.class);

        // then
        // 내 정보가 조회된다.
        assertAll(
                () -> assertThat(customerResponse.getUserId())
                        .isEqualTo(customerRequest.getUserId()),
                () -> assertThat(customerResponse.getNickname())
                        .isEqualTo(customerRequest.getNickname())
        );
    }

    @Test
    void 부적절한_토큰은_예외를_발생시킨다() {
        // given
        // 부적절한 토큰이 존재한다.
        String invalidToken = "jojogreen";

        // when then
        // 부적절한 토큰으로 내 정보를 조회하면 예외를 발생시킨다.
        ExtractableResponse<Response> response = 나의_정보조회(invalidToken);
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(response.body().as(ExceptionRequest.class).getMessage())
                        .isEqualTo("권한이 없습니다.")
        );
    }

    @Test
    void 만료된_토큰은_예외를_발생시킨다() {
        // given
        // 만료된 토큰이 존재한다.
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000);
        SecretKey secretKey = Keys.hmacShaKeyFor(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ih1aovtQShabQ7l0cINw4k1fagApg3qLWiB8Kt59Lno".getBytes(
                        StandardCharsets.UTF_8));
        String expiredToken = Jwts.builder()
                .setSubject("1")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // when then
        // 만료된 토큰으로 내 정보를 조회하면 예외를 발생시킨다.
        ExtractableResponse<Response> response = 나의_정보조회(expiredToken);
        assertAll(
                () -> assertThat(response.statusCode())
                        .isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(response.body().as(ExceptionRequest.class).getMessage())
                        .isEqualTo("권한이 없습니다.")
        );
    }
}
