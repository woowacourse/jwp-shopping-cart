package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("이메일은 숫자, 영어 소문자, 언더바로만 구성된 형식이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "AAA@aa.com", "aaa@Aa.com", "aaa.com", "aaa@aa"})
    void exceptionWhenWrongEmailFormat(String email) {
        // when, then
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일은 숫자, 알파벳 소문자, _로만 구성할 수 있습니다.");
    }

    @DisplayName("형식에 맞는 이메일은 생성 가능하다.")
    @ParameterizedTest
    @ValueSource(strings = {"aaa@aa.com", "a1@aa.com"})
    void successWhenRightFormat(String email) {
        // when, then
        assertDoesNotThrow(() -> new Email(email));
    }
}