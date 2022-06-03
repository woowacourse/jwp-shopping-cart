package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixture.페퍼_아이디;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰의 페이로드를 변환할 수 있다")
    void getPayload() {
        //given
        String token = jwtTokenProvider.createToken(페퍼_아이디);

        //when
        String payload = jwtTokenProvider.getValidatedPayload(token);

        //then
        assertThat(payload).isEqualTo(페퍼_아이디);
    }
}
