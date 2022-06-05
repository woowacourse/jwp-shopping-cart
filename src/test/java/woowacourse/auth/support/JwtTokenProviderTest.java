package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixture.페퍼_아이디;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.exception.auth.InvalidTokenException;

@SpringBootTest
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰을 생성하고, 토큰의 페이로드를 변환할 수 있다")
    void getValidatedPayload() {
        //given
        String token = jwtTokenProvider.createToken(페퍼_아이디);

        //when
        String payload = jwtTokenProvider.getValidatedPayload(token);

        //then
        assertThat(payload).isEqualTo(페퍼_아이디);
    }

    @Test
    @DisplayName("가짜 토큰을 받으면 예외를 던진다.")
    void validateExpirationDate() {
        //given
        String token = "FakeToken";

        //when
        assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(token))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("인증이 유효하지 않습니다.");

    }
}
