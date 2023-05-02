package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.entity.Member;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberServiceTest {

    private MemberService memberService;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = Mockito.mock(MemberDao.class);
        memberService = new MemberService(memberDao);
    }

    @DisplayName("회원을 전체 조회한다")
    @Test
    void findMembers() {
        given(memberDao.findAll()).willReturn(
                List.of(new Member("boxster@email.com", "boxster"),
                        new Member("gitchan@email.com", "gitchan"))
        );

        List<Member> members = memberService.findMembers();

        assertThat(members).hasSize(2);
    }
}
