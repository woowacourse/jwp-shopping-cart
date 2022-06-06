package woowacourse.shoppingcart.unit.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰의 payload를 검증한다.")
    void getPayload() {
        // given
        final String email = "kun@naver.com";
        final String token = jwtTokenProvider.createToken(email);

        // when
        final String actual = jwtTokenProvider.getPayload(token);

        // then
        assertThat(actual).isEqualTo(email);
    }

    @Test
    @DisplayName("유효한 토큰을 검증하면 true를 반환한다.")
    void validateToken_validToken_trueReturned() {
        // given
        final String email = "kun@naver.com";
        final String token = jwtTokenProvider.createToken(email);

        // when
        final boolean actual = jwtTokenProvider.validateToken(token);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 토큰을 검증하면 false를 반환한다.")
    void validateToken_invalidToken_falseReturned() {
        // given
        final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNrQG5hdmVyLmNvbSIsImlhdCI6MTY1MzkxMDkyOSwiZXhwIjoxNjUzOTE0NTI5fQ.BTqxwStzF2UpSznVp3X1QiDMoaNo4xZFd2DDvQjui5w";

        // when
        final boolean actual = jwtTokenProvider.validateToken(token);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("값이 null인 토큰을 검증하면 false를 반환한다.")
    void validateToken_nullToken_falseReturned() {
        // given
        final String token = null;

        // when
        final boolean actual = jwtTokenProvider.validateToken(token);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("payload가 동일하고 생성 시점이 같지 않으면 다른 토큰이다.")
    void validateToken_invalidToken_falseReturned2() {
        // given
        final String expected = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaWNrQG5hdmVyLmNvbSIsImlhdCI6MTY1MzkxMDkyOSwiZXhwIjoxNjUzOTE0NTI5fQ.BTqxwStzF2UpSznVp3X1QiDMoaNo4xZFd2DDvQjui5w";

        // when
        final String actual = jwtTokenProvider.createToken("rick@naver.com");

        // then
        assertThat(actual).isNotEqualTo(expected);
    }
}
