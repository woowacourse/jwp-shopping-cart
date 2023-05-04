package cart.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    MemberDao memberDao;

    @InjectMocks
    MemberService memberService;

    @Test
    void 멤버를_생성한다() {
        given(memberDao.save(any()))
                .willReturn(new Member(1L, "cyh6099@gmail.com", "qwer1234"));

        long createdMemberId = memberService.createMember("cyh6099@gmail.com", "qwer1234");

        Assertions.assertThat(createdMemberId).isEqualTo(1L);
    }
}
