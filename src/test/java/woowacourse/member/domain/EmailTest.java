package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.member.exception.InvalidMemberEmailException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @DisplayName("이메일은 @이 포함된 올바른 형식이어야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"tonywooteco.com", "tony@", "@tony","tony@wootecocom","to.ny.@.wooteco.com"})
    void emailContainsAt() {
        assertThatThrownBy(() -> new Email("wooteconaver.com"))
                .isInstanceOf(InvalidMemberEmailException.class)
                .hasMessageContaining("올바르지 못한 이메일 형식입니다.");
    }

}
