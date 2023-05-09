package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일을 입력 받아 생성한다.")
    @Test
    void create() {
        //given
        String value = "email@email.com";
        //when
        Email email = new Email(value);
        //then
        assertThat(email.getValue()).isEqualTo(value);
    }


    @DisplayName("빈 값을 입력 받으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void createExceptionWithEmpty(final String value) {
        //then
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("100자 이상을 입력 받으면 예외를 발생한다.")
    @Test
    void createExceptionWithOver100() {
        //given
        String value = ".".repeat(101);
        //then
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
