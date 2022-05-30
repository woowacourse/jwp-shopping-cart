package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PasswordTest {

    @ParameterizedTest(name = "비밀번호 : {0}")
    @ValueSource(strings = {"gusghgusgh", "GUSGHGUSGH", "12345678", "현호현호현호현호", "!!!!!!!!"})
    void 대문자_소문자_숫자_중_2종류_미만_생성_예외(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Password(value))
                .withMessage(
                        String.format("비밀번호는 대문자, 소문자, 숫자 중 2종류 이상으로 생성 가능합니다. 입력값: %s", value));
    }
}
