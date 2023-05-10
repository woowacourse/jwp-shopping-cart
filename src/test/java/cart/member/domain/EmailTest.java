package cart.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    private static final String REGULAR_DOMAIN = "@google.com";

    @Test
    @DisplayName("이메일의 형식을 지키면 이메일이 정상생성됩니다")
    void acceptedEmail() {
        assertThatNoException().isThrownBy(() -> new Email("a".repeat(100 - REGULAR_DOMAIN.length()) + REGULAR_DOMAIN));
    }

    @ParameterizedTest(name = "이메일의 형식을 지키지 않으면 예외를 발생시킵니다")
    @ValueSource(strings = {" @.com", "abc@", "abc@  .com", "abc.com"})
    void unacceptableEmail(String address) {
        assertThatThrownBy(() -> new Email(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 주소 형식입니다");
    }

    @Test
    @DisplayName("이메일의 길이 제한을 지키지 않으면 예외를 발생시킵니다")
    void unacceptableEmailLength() {
        String address = "a".repeat(100 - REGULAR_DOMAIN.length() + 1);

        assertThatThrownBy(() -> new Email(address + REGULAR_DOMAIN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 길이는 100자까지 입니다");
    }
}
