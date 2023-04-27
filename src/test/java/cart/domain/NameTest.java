package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("이름을 받아 정상적으로 생성한다")
    @Test
    void create() {
        //when
        String value = "레드북";
        Name name = new Name(value);
        //then
        assertThat(name.getValue()).isEqualTo(value);
    }

    @DisplayName("비어있는 이름을 받으면 예외를 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createExceptionWithEmptyName(final String value) {
        //then
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("30자를 초과하는 이름을 받으면 예외를 반환한다")
    @Test
    void createExceptionWithOver30() {
        //given
        String value = "0123456789012345678901234567890";
        //then
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
