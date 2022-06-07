package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static woowacourse.Fixture.페퍼_아이디;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("JwtTokenProviderTest 클래스의")
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("getPayload 함수는")
    class getPayload {

        @Test
        @DisplayName("getPayload 함수는 토큰의 페이로드를 변환할 수 있다.")
        void success() {
            //given
            String token = jwtTokenProvider.createToken(페퍼_아이디);

            //when
            String payload = jwtTokenProvider.getPayload(token);

            //then
            assertThat(payload).isEqualTo(페퍼_아이디);
        }

        @Test
        @DisplayName("토큰이 유효하지 않은 경우 예외를 던진다.")
        void expiredToken() {
            // given
            String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXBwZXJAd29vd2Fjb3Vyc2UuY29tIiwiaWF0IjoxNjU0NDkzMDM0LCJleHAiOjE2NTQ0OTMxMzR9.CiCFn0f42ro8nhoH__Fs2wXQTPi5g8GVTN7Ae4sc5k_2RQNNAx0gcxtaDcxDZgLbqk7ploc7GJxZUfXTWnq3uQ";

            // when & then
            assertThatThrownBy(() -> jwtTokenProvider.getPayload(expiredToken))
                    .isInstanceOf(ExpiredJwtException.class);
        }
    }

    @Nested
    @DisplayName("validateToken 함수는")
    class validateToken {

        @Test
        @DisplayName("토큰이 유효한 경우 true를 반환한다.")
        void isValid() {
            // given
            String token = jwtTokenProvider.createToken(페퍼_아이디);

            // when & then
            assertTrue(jwtTokenProvider.validateToken(token));
        }

        @Test
        @DisplayName("토큰이 유효하지 않은 경우 false를 반환한다.")
        void isNotValid() {
            // given & when & then
            assertFalse(jwtTokenProvider.validateToken("hello"));
        }
    }
}
