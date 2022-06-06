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
        System.out.println("token = " + token);

        //when
        String payload = jwtTokenProvider.getValidatedPayload(token);

        //then
        assertThat(payload).isEqualTo(페퍼_아이디);
    }

    @Test
    @DisplayName("가짜 토큰을 받으면 예외를 던진다.")
    void validateFakeToken() {
        //given
        String fakeToken = "FakeToken";

        //when
        assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(fakeToken))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("인증이 유효하지 않습니다.");
    }

    @Test
    @DisplayName("만료기간이 지난 토큰을 받으면 예외를 던진다.")
    void validateExpirationDate() {
        //given
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXBwZXJAd29vd2Fjb3Vyc2UuY29tIiwiaWF0IjoxNjU0NDkzMDM0LCJleHAiOjE2NTQ0OTMxMzR9.CiCFn0f42ro8nhoH__Fs2wXQTPi5g8GVTN7Ae4sc5k_2RQNNAx0gcxtaDcxDZgLbqk7ploc7GJxZUfXTWnq3uQ";

        //when
        assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(expiredToken))
                .isInstanceOf(InvalidTokenException.class)
                .hasMessage("인증이 유효하지 않습니다.");
    }
}
