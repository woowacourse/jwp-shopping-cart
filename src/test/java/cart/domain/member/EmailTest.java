package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EmailTest {

    @Test
    @DisplayName("이메일을 정상적으로 생성한다.")
    void create_email_success() {
        // given
        String email = "sosow0212@naver.com";

        // when & then
        assertDoesNotThrow(() -> new Email(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abc@", "", " "})
    @DisplayName("이메일 생성에 실패한다.")
    void throws_exception_when_email_is_wrong(final String givenEmail) {
        // when & then
        assertThatThrownBy(() -> new Email(givenEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
