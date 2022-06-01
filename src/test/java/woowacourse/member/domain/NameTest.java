package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.exception.InvalidMemberNameException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @DisplayName("이름은 공백이 포함되지 않아야 한다.")
    @Test
    void nameNotContainSpace() {
        assertThatThrownBy(() -> new Name("woote co"))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름에 공백이 포함될 수 없습니다.");
    }

    @DisplayName("이름은 10자 이하여야 한다.")
    @Test
    void nameLessThan10Letters() {
        assertThatThrownBy(() -> new Name("ILoveWooteco"))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름은 10자 이하이어야 합니다.");
    }

}
