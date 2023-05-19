package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class EmailTest {

    @DisplayName("이메일을 받아 정상적으로 생성한다")
    @Test
    void create() {
        //when
        String value = "test@email.com";
        Email email = new Email(value);
        //then
        assertThat(email.getValue()).isEqualTo(value);
    }

    @DisplayName("비어있는 이메일을 받으면 예외를 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createExceptionWithEmptyName(final String value) {
        //then
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("50자를 초과하는 이메일을 받으면 예외를 반환한다")
    @Test
    void createExceptionWithOver30() {
        //given
        String value = "0123456789012345678901234567890012345678901234567890";
        //then
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
