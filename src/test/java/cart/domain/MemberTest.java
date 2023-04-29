package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @Test
    @DisplayName("사용자가 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Member member = new Member("glen@naver.com", "123456");

        // expect
        assertThat(member.getEmail())
                .isEqualTo("glen@naver.com");
        assertThat(member.getPassword())
                .isEqualTo("123456");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("이메일에 빈 값이 들어오면 예외가 발생한다.")
    void create_blankEmail(String email) {
        // expect
        assertThatThrownBy(() -> new Member(email, "123456"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {":aaa@naver.com", "aaa@na:ver.com", "aaa@naver.com:"})
    @DisplayName("이메일에 :이 포함되면 예외가 발생해야 한다.")
    void create_containColonEmail(String email) {
        // expect
        assertThatThrownBy(() -> new Member(email, "123456"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일에 \":\"가 포함될 수 없습니다.");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("비밀번호에 빈 값이 들어오면 예외가 발생한다.")
    void create_blankPassword(String password) {
        // expect
        assertThatThrownBy(() -> new Member("glen@naver.com", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123:456", ":123456", "123456:"})
    @DisplayName("비밀번호에 :이 포함되면 예외가 발생해야 한다.")
    void create_containColonPassword(String password) {
        // expect
        assertThatThrownBy(() -> new Member("glen@naver.com", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호에 \":\"가 포함될 수 없습니다.");
    }
}
