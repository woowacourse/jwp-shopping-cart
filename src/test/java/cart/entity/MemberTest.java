package cart.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.EmailFormatNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @ParameterizedTest
    @DisplayName("이메일 형식이 {숫자,_,영문자}@{숫자,_,영문자}.{숫자,_,영문자}이 아니면 Exception을 발생시킨다.")
    @ValueSource(strings = {"wooteco.c@om", "wooteco.com", "wooteco@.com"})
    void validateEmailFormat(final String email) {
        assertThatThrownBy(() -> new Member(1L, email, "password"))
                .isInstanceOf(EmailFormatNotValidException.class);
    }
}
