package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class NicknameTest {

    @ParameterizedTest(name = "닉네임 : {0}")
    @ValueSource(strings = {" 호호 ", "호호 ", " 호호"})
    void 앞뒤_공백_제거_후_생성(String value) {
        Nickname nickname = new Nickname(value);
        Nickname expect = new Nickname("호호");

        assertThat(nickname).isEqualTo(expect);
    }

    @ParameterizedTest(name = "닉네임 : {0}")
    @ValueSource(strings = {"연", "연로그연로그연로그연로"})
    void 올바르지_않은_글자수_닉네_생성_예외(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Nickname(value))
                .withMessage(String.format("닉네임은 2 ~ 10자로 생성 가능합니다. 입력값: %s", value));
    }
}
