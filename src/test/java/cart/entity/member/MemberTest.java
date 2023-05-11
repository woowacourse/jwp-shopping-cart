package cart.entity.member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("사용자가 admin 유저이면 true를 반환한다.")
    void member_is_admin() {
        // given
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"), Role.ADMIN);

        // when
        boolean result = member.isAdminUser();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사용자가 일반 유저이면 false를 반환한다.")
    void member_is_user() {
        // given
        Member member = new Member(1L, new Email("ako@wooteco.com"), new Password("ako"), Role.USER);

        // when
        boolean result = member.isAdminUser();

        // then
        assertThat(result).isFalse();
    }
}
