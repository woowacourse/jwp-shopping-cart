package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    @DisplayName("Member를 생성할 수 있다.")
    void create() {
        // given
        final Long id = 1L;
        final String email = "mango@wooteco.com";
        final String password = "passwd";
        // when
        final Member member = new Member(1L, new Email(email), new Password(password));
        // then
        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getEmail().getValue()).isEqualTo(email);
        assertThat(member.getPassword().getValue()).isEqualTo(password);
    }
}
