package cart.service;

import cart.dao.member.JdbcMemberDao;
import cart.domain.member.Member;
import cart.dto.MemberResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static cart.DummyData.DUMMY_MEMBER_ONE;
import static cart.DummyData.INITIAL_MEMBER_ONE;
import static cart.DummyData.INITIAL_MEMBER_TWO;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    JdbcMemberDao memberDao;

    @Test
    void 모든_멤버_목록을_가져와서_반환하는지_확인한다() {
        final List<Member> data = List.of(INITIAL_MEMBER_ONE, INITIAL_MEMBER_TWO);
        when(memberDao.findAll()).thenReturn(data);

        final List<MemberResponse> memberResponses = memberService.findAll();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(memberResponses.size()).isEqualTo(data.size());
            softAssertions.assertThat(memberResponses.get(0).getId()).isEqualTo(INITIAL_MEMBER_ONE.getId());
            softAssertions.assertThat(memberResponses.get(0).getEmail()).isEqualTo(INITIAL_MEMBER_ONE.getEmail());
            softAssertions.assertThat(memberResponses.get(0).getPassword()).isEqualTo(INITIAL_MEMBER_ONE.getPassword());
            softAssertions.assertThat(memberResponses.get(1).getId()).isEqualTo(INITIAL_MEMBER_TWO.getId());
            softAssertions.assertThat(memberResponses.get(1).getEmail()).isEqualTo(INITIAL_MEMBER_TWO.getEmail());
            softAssertions.assertThat(memberResponses.get(1).getPassword()).isEqualTo(INITIAL_MEMBER_TWO.getPassword());
        });
    }

    @Test
    void 특정_멤버를_가져와서_반환하는지_확인한다() {
        when(memberDao.findByEmailAndPassword(any(), any())).thenReturn(DUMMY_MEMBER_ONE);

        final Member actual = memberService.find(DUMMY_MEMBER_ONE.getEmail(), DUMMY_MEMBER_ONE.getPassword());

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(actual.getId()).isEqualTo(DUMMY_MEMBER_ONE.getId());
            softAssertions.assertThat(actual.getEmail()).isEqualTo(DUMMY_MEMBER_ONE.getEmail());
            softAssertions.assertThat(actual.getPassword()).isEqualTo(DUMMY_MEMBER_ONE.getPassword());
        });
    }
}
