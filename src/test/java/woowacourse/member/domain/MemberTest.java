package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.InvalidMemberNameException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @DisplayName("이름은 공백이 포함되지 않아야 한다.")
    @Test
    void nameNotContainSpace() {
        assertThatThrownBy(() -> new Member("wooteco@naver.com", "woote co", "Wooteco1!"))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름에 공백이 포함될 수 없습니다.");
    }

    @DisplayName("이름은 10자 이하여야 한다.")
    @Test
    void nameLessThan10Letters() {
        assertThatThrownBy(() -> new Member("wooteco@naver.com", "ILoveWooteco", "Wooteco1!"))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름은 10자 이하이어야 합니다.");
    }

    @DisplayName("이메일은 @이 포함된 올바른 형식이어야 한다.")
    @Test
    void emailContainsAt() {
        assertThatThrownBy(() -> new Member("wooteconaver.com", "wooteco", "Wooteco1!"))
                .isInstanceOf(InvalidMemberEmailException.class)
                .hasMessageContaining("올바르지 못한 이메일 형식입니다.");
    }
}
