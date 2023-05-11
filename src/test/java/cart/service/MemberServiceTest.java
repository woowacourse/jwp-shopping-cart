package cart.service;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("저장되어 있는 모든 멤버를 조회하여 MemberResponse의 List 타입으로 반환한다.")
    @Test
    void findAll() {
        // given
        Member member1 = new Member(1L, "a@a.com", "password1");
        Member member2 = new Member(2L, "b@b.com", "password2");
        given(memberDao.findAll()).willReturn(List.of(member1, member2));

        // when
        List<MemberResponse> memberResponses = memberService.findAll();

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberResponses.size()).isEqualTo(2);
            softly.assertThat(memberResponses.get(0))
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(member1);
        });
    }
}
