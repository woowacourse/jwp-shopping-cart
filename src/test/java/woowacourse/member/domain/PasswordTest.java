package woowacourse.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.helper.fixture.MemberFixture.ENCODE_PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.infrastructure.SHA256PasswordEncoder;

public class PasswordTest {

    @DisplayName("암호화 된 패스워드를 생성한다")
    @Test
    void validateEncodedDoseNotThrow() {
        assertDoesNotThrow(() -> Password.fromEncoded(ENCODE_PASSWORD));
    }

    @DisplayName("암호화 되지 않은 패스워드를 생성하면 오류를 발생한다")
    @Test
    void validateEncodedThrowException() {
        assertThatThrownBy(() -> Password.fromEncoded(PASSWORD))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("암호화 되지 않은 패스워드를 생성한다")
    @Test
    void validateNotEncodedDoseNotThrow() {
        assertDoesNotThrow(() -> Password.fromNotEncoded(PASSWORD, new SHA256PasswordEncoder()));
    }

    @DisplayName("암호화 된 패스워드를 생성하면 오류를 발생한다")
    @Test
    void validateNotEncodedThrowException() {
        assertThatThrownBy(() -> Password.fromNotEncoded(ENCODE_PASSWORD, new SHA256PasswordEncoder()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
