package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.LengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @Test
    @DisplayName("회원이 생성된다.")
    void createSuccess() {
        Member member = new Member("aaa@naver.com", "test", "gray");

        assertThat(member).isNotNull();
    }

    @Test
    @DisplayName("이메일이 빈 값인 경우 예외가 발생한다.")
    void createFailWithWrongEmail() {
        assertThatThrownBy(() -> new Member(" ", "test", "gray"))
                .isInstanceOf(LengthException.class)
                .hasMessageContaining("이메일은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "abc", "hellohellohello"})
    @DisplayName("비밀번호가 4자 이상 12자 이하가 아닌 경우 예외가 발생한다.")
    void createFailWithWrongPassword(String password) {
        assertThatThrownBy(() -> new Member("aaa@naver.com", password, "gray"))
                .isInstanceOf(LengthException.class)
                .hasMessageContaining("비밀번호는 최소 4자 최대 12자까지 가능합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "gradle"})
    @DisplayName("이름이 1자 이상 5자 이하가 아닌 경우 예외가 발생한다.")
    void createFailWithWrongName(String name) {
        assertThatThrownBy(() -> new Member("aaa@naver.com", "test", name))
                .isInstanceOf(LengthException.class)
                .hasMessageContaining("이름의 길이는 최소 2자 최대 5자까지 가능합니다.");
    }

}
