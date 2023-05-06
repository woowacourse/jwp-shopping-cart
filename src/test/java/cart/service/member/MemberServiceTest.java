package cart.service.member;

import cart.service.member.domain.Member;
import cart.service.member.dto.MemberServiceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.empty());

        given(memberDao.save(any()))
                .willReturn(new Member(1L, "cyh6099@gmail.com", "qwer1234"));

        long createdMemberId = memberService.createMember(new MemberServiceRequest("cyh6099@gmail.com", "qwer1234"));

        assertThat(createdMemberId).isEqualTo(1L);
    }

    @Test
    void 중복_이메일은_예외가_발생한다() {
        given(memberDao.findByEmail(any()))
                .willReturn(Optional.of(new Member(1L, "cyh6099@gamil.com", "qwer1234")));

        assertThatThrownBy(() ->
                memberService.createMember(new MemberServiceRequest("cyh6099@wooteco.com", "qwer1234")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이메일은 이미 존재합니다.");
    }
}
