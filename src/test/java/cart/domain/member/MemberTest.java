package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {
    private static final String EMAIL = "glen@naver.com";
    private static final String PASSWORD = "123456";

    @Test
    @DisplayName("사용자가 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Member member = new Member(new Email(EMAIL), new Password(PASSWORD));

        // expect
        assertThat(member.getEmail())
                .isEqualTo("glen@naver.com");
        assertThat(member.getPassword())
                .isEqualTo("123456");
    }
}
