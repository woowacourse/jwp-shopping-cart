package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.domain.password.Password;
import woowacourse.member.domain.password.PlainPassword;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordTest {

    @DisplayName("같은 비밀번호로 만들어진 경우 동일하다고 판단한다.")
    @Test
    void equals() {
        PlainPassword plainPassword = new PlainPassword("Wooteco123!");
        Password password = plainPassword.encrypt();
        PlainPassword comparisonPlainPassword = new PlainPassword("Wooteco123!");
        Password comparison = comparisonPlainPassword.encrypt();
        boolean result = password.equals(comparison);
        assertThat(result).isTrue();
    }

    @DisplayName("다른 비밀번호로 만들어진 경우 동등하지 않다고 판단한다.")
    @Test
    void notEquals() {
        PlainPassword plainPassword = new PlainPassword("Wooteco123!");
        Password password = plainPassword.encrypt();
        PlainPassword comparisonPlainPassword = new PlainPassword("Wooteco1!");
        Password comparison = comparisonPlainPassword.encrypt();
        boolean result = password.equals(comparison);
        assertThat(result).isFalse();
    }
}
