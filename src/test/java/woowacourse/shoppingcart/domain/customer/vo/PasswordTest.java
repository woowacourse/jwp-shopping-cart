package woowacourse.shoppingcart.domain.customer.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PasswordTest {

    private static final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @ParameterizedTest(name = "비밀번호 : {0}")
    @ValueSource(strings = {"gusghgusgh", "GUSGHGUSGH", "12345678", "현호현호현호현호", "!!!!!!!!",
            "aaaa1111", "AAAA1111", "aaaa!!!!", "1111!!!!", "AAAA!!!!",
            "abcABC123", "ABC123!@#", "abc123!@#", "abcABC!@#"})
    void 대소문자_숫자_특수문자_미포함_검증(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Password.plainText(value))
                .withMessage("비밀번호는 대소문자, 숫자, 특수 문자를 포함해야 생성 가능합니다.");
    }

    @ParameterizedTest(name = "비밀번호 : {0}")
    @ValueSource(strings = {"aA!4567", "aA!456789012345678901"})
    void 올바르지_않은_글자수_비밀번호_검증(String value) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Password.plainText(value))
                .withMessage("비밀번호는 8 ~ 20자로 생성 가능합니다.");
    }

    @Test
    void 비밀번호_암호화() {
        String input = "leoLeo84!";
        Password password = Password.plainText(input);

        assertThat(password.getValue()).isNotEqualTo(input);
    }

    @Test
    void 암호화된_비밀번호_일치_여부() {
        String input = "leoLeo84!";
        Password actual = Password.plainText(input);
        String expected = passwordEncoder.encrypt(input);

        assertThat(actual.getValue()).isEqualTo(expected);
    }
}
