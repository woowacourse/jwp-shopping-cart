package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class PasswordTest {

    @DisplayName("비밀번호를 받아 정상적으로 생성한다")
    @Test
    void create() {
        //when
        String value = "test@email.com";
        Password password = new Password(value);
        //then
        assertThat(password.getValue()).isEqualTo(value);
    }

    @DisplayName("비어있는 비밀번호를 받으면 예외를 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createExceptionWithEmptyName(final String value) {
        //then
        assertThatThrownBy(() -> new Password(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("20자를 초과하는 비밀번호를 받으면 예외를 반환한다")
    @Test
    void createExceptionWithOver30() {
        //given
        String value = "012345678901234567890";
        //then
        assertThatThrownBy(() -> new Password(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
