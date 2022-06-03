package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import woowacourse.auth.domain.Token;

@SuppressWarnings("NonAsciiCharacters")
class JwtTokenServiceTest {

    private JwtTokenService tokenService;

    @BeforeEach
    void setUp() {
        String secretKey = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        int validity = 36000;
        tokenService = new JwtTokenService(secretKey, validity);
    }

    @Test
    void 생성된_토큰에서_원본값_추출_가능() {
        String 토큰에_담기는_정보 = "payload";

        Token 토큰 = tokenService.generateToken(토큰에_담기는_정보);
        String 토큰에서_추출되는_정보 = tokenService.extractPayload(토큰);

        assertThat(토큰에_담기는_정보).isEqualTo(토큰에서_추출되는_정보);
    }

    @Test
    void 다른_데이터로부터_생성된_토큰은_다른_값_추출됨() {
        String 정보 = "payload1";
        String 다른_정보 = "payload2";
        Token 토큰 = tokenService.generateToken(정보);
        Token 다른_토큰 = tokenService.generateToken(다른_정보);

        String 토큰에서_추출된_정보 = tokenService.extractPayload(토큰);
        String 다른_토큰에서_추출된_정보 = tokenService.extractPayload(다른_토큰);

        assertThat(토큰에서_추출된_정보).isNotEqualTo(다른_토큰에서_추출된_정보);
    }
}
