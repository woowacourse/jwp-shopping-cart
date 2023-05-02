package cart.business.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberPasswordTest {

    @Test
    @DisplayName("비밀번호가 10자 초과라면 예외를 던진다")
    void test_size_exceed() {
        String password = "12345678901";

        assertThatThrownBy(() -> new MemberPassword(password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 10자 미만이라면 정상 생성된다")
    void test_size_normal() {
        String password = "1234567890";

        assertThatCode(() -> new MemberPassword(password))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호가 4자 미만이라면 예외를 던진다")
    void test_size_low() {
        String password = "123";

        assertThatThrownBy(() -> new MemberPassword(password))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
