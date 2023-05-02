package cart.business.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberEmailTest {

    @Test
    @DisplayName("이메일이 올바른 형식이 아니라면 예외를 던진다")
    void test_pattern_exception() {
        String email = "abcde";

        assertThatThrownBy(() -> new MemberEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일이 올바른 형식이라면 정상 생성된다")
    void test_pattern_normal() {
        String email = "dntjd991223@naver.com";

        assertThatCode(() -> new MemberEmail(email))
                .doesNotThrowAnyException();
    }
}
