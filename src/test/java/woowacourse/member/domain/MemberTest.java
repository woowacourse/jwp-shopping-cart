package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.domain.password.Password;
import woowacourse.member.domain.password.PlainPassword;
import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.InvalidMemberNameException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    private final PlainPassword plainPassword = new PlainPassword("Wooteco1!");
    private final Password password = plainPassword.encrypt();

    @DisplayName("이름은 공백이 포함되지 않아야 한다.")
    @Test
    void nameNotContainSpace() {
        assertThatThrownBy(() -> new Member("wooteco@naver.com", "woote co", password))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름에 공백이 포함될 수 없습니다.");
    }

    @DisplayName("이름은 10자 이하여야 한다.")
    @Test
    void nameLessThan10Letters() {
        assertThatThrownBy(() -> new Member("wooteco@naver.com", "ILoveWooteco", password))
                .isInstanceOf(InvalidMemberNameException.class)
                .hasMessageContaining("이름은 10자 이하이어야 합니다.");
    }

    @DisplayName("이메일은 @이 포함된 올바른 형식이어야 한다.")
    @Test
    void emailContainsAt() {
        assertThatThrownBy(() -> new Member("wooteconaver.com", "wooteco", password))
                .isInstanceOf(InvalidMemberEmailException.class)
                .hasMessageContaining("올바르지 못한 이메일 형식입니다.");
    }


    @DisplayName("비밀번호가 일치한다면 true를 반환한다.")
    @Test
    void isSamePassword() {
        PlainPassword plainPassword = new PlainPassword("Wooteco123!");
        Password password = plainPassword.encrypt();
        Member member = new Member("wooteco@naver.com", "우테코", password);
        boolean result = member.isSamePassword(password);
        assertThat(result).isTrue();
    }

    @DisplayName("비밀번호가 일치하지 않는다면 false를 반환한다.")
    @Test
    void isNotSamePassword() {
        PlainPassword plainPassword = new PlainPassword("Wooteco123!");
        Password password = plainPassword.encrypt();
        PlainPassword wrongPlainPassword = new PlainPassword("Wooteco123?");
        Password wrongPassword = wrongPlainPassword.encrypt();
        Member member = new Member("wooteco@naver.com", "우테코", password);
        boolean result = member.isSamePassword(wrongPassword);
        assertThat(result).isFalse();
    }
}
