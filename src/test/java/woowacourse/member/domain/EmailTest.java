package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.exception.InvalidMemberEmailException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일은 @이 포함된 올바른 형식이어야 한다.")
    @Test
    void emailContainsAt() {
        assertThatThrownBy(() -> new Email("wooteconaver.com"))
                .isInstanceOf(InvalidMemberEmailException.class)
                .hasMessageContaining("올바르지 못한 이메일 형식입니다.");
    }

}
