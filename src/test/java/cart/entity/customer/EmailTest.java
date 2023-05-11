package cart.entity.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @DisplayName("이메일에 @가 존재하지 않을 경우 오류를 던진다.")
    @Test
    void constructor_failWithoutSeperator() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Email("splitWooteco.com"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이메일에 .가 존재하지 않을 경우 오류를 던진다.")
    @Test
    void constructor_failWithoutDot() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Email("split@Wootecocom"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바른 이메일 형식을 통하여 이메일 객체를 생성한다.")
    @Test
    void validate() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> new Email("split@Wooteco.com"));
    }
}
