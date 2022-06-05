package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("이름이 같다면 true를 반환한다.")
    void isSameNameWhenTrue() {
        Member member = new Member("wooteco@email.com", "렉스", new InputPassword("Wooteco1!"));
        assertThat(member.isSameName(new Name("렉스"))).isTrue();
    }

    @Test
    @DisplayName("이름이 다르다면 false를 반환한다.")
    void isSameNameWhenFalse() {
        Member member = new Member("wooteco@email.com", "렉스", new InputPassword("Wooteco1!"));
        assertThat(member.isSameName(new Name("롤렉스"))).isFalse();
    }

    @Test
    @DisplayName("비밀번호가 같다면 true를 반환한다.")
    void isSamePasswordWhenTrue() {
        Member member = new Member("wooteco@email.com", "렉스", new InputPassword("Wooteco1!"));
        assertThat(member.isSamePassword(new InputPassword("Wooteco1!"))).isTrue();
    }

    @Test
    @DisplayName("비밀번호가 다르다면 false를  반환한다.")
    void isSamePasswordWhenFalse() {
        Member member = new Member("wooteco@email.com", "렉스", new InputPassword("Wooteco1!"));
        assertThat(member.isSamePassword(new InputPassword("Fake1!"))).isFalse();
    }

}
