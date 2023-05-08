package cart.member.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
class EmailTest {
    @ParameterizedTest(name = "{displayName} : email = {0}")
    @NullAndEmptySource
    void 이메일_null_또는_empty_입력_시_예외_처리(String email) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Email(email))
                .withMessage("[ERROR] 이메일이 비어있습니다.");
    }
    
    @ParameterizedTest(name = "{displayName} : email = {0}")
    @ValueSource(strings = {"a@a.com", "abel@abel.com"})
    void 이메일_정상_입력(String email) {
        assertThatNoException()
                .isThrownBy(() -> new Email(email));
    }
    
    @ParameterizedTest(name = "{displayName} : email = {0}")
    @ValueSource(strings = {"a@.com", "@abel.com", " @abel.com", "a@abel", "a@abel."})
    void 이메일_형식이_올바르지_않을_시_예외_처리(String email) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Email(email))
                .withMessage("[ERROR] 이메일 형식이 올바르지 않습니다.");
    }
}
