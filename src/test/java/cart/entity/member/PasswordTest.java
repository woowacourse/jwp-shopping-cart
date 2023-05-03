package cart.entity.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import cart.exception.common.NullOrBlankException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @ParameterizedTest(name = "{displayName}")
    @NullSource
    @ValueSource(strings = {" ", "", "   "})
    @DisplayName("비밀번호가 {0}이면 에러를 발생시킨다.")
    void validate_email_null_or_blank(String email) {
        // when + then
        assertThatThrownBy(() -> new Email(email))
            .isInstanceOf(NullOrBlankException.class);

    }

}