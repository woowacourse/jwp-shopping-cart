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
    @ValueSource(strings = {"gusghgusgh", "GUSGHGUSGH", "12345678", "현호현호현호현호", "!!!!!!!!",
            "aaaa1111", "AAAA1111", "aaaa!!!!", "1111!!!!", "AAAA!!!!",
            "abcABC123", "ABC123!@#", "abc123!@#", "abcABC!@#"})
    void 대소문자_숫자_특수문자_미포함_생성_예외(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Password(value))
                .withMessage("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @ParameterizedTest(name = "비밀번호 : {0}")
    @ValueSource(strings = {"aA!4567", "aA!456789012345678901"})
    void 올바르지_않은_글자수_비밀번호_생성_예외(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Password(value))
                .withMessage("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

}
