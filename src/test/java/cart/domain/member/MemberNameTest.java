package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MemberNameTest {

    @Test
    @DisplayName("이름이 null 일 경우 예외 발생")
    void nameNull() {
        assertThatThrownBy(() -> new MemberName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름의 길이가 0일 경우 예외 발생")
    void nameEmpty() {
        assertThatThrownBy(() -> new MemberName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MemberName.MEMBER_NAME_LENGTH_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("이름의 길이가 0일 경우 예외 발생")
    @ValueSource(strings = {"", " "})
    void nameBlank(String name) {
        assertThatThrownBy(() -> new MemberName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MemberName.MEMBER_NAME_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이름의 길이가 10 초과일 경우 예외 발생")
    void nameLengthMoreThanMax() {
        String name = "a".repeat(11);
        assertThatThrownBy(() -> new MemberName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MemberName.MEMBER_NAME_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이름의 길이가 정상일 경우")
    void nameSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new MemberName("이")),
                () -> assertDoesNotThrow(() -> new MemberName("a".repeat(10)))
        );
    }
}
