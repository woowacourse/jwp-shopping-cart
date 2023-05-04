package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.domain.member.Member;
import cart.exception.auth.UnauthenticatedException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberServiceTest {

    private MemberDao memberDao;
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberDao = Mockito.mock(MemberDao.class);
        memberService = new MemberService(memberDao);
    }

    @Test
    @DisplayName("이메일과 비밀번호로 로그인한다")
    void login() {
        // given
        given(memberDao.findByEmailAndPassword("a@a.com", "password1"))
                .willReturn(Optional.of(new Member((long) 1, "a@a.com", "password1", "애쉬")));

        // when
        Member member = memberService.login("a@a.com", "password1");

        // then
        assertThat(member.getName()).isEqualTo("애쉬");
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하지 않을 경우 예외가 발생한다")
    void login_throws() {
        // given
        given(memberDao.findByEmailAndPassword("a@a.com", "password1"))
                .willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> memberService.login("b@b.com", "password3"))
                .isInstanceOf(UnauthenticatedException.class);
    }
}
