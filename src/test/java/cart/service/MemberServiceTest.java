package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.controller.exception.MemberNotFoundException;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.MemberDto;
import cart.dto.response.MemberResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    public static final Member MEMBER_FIXTURE = new Member(1L, "gavi@woowahan.com", "1234");

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 모든_회원을_조회한다() {
        //given
        Mockito.when(memberDao.findAll())
                .thenReturn(Optional.ofNullable(List.of(MEMBER_FIXTURE)));

        //when
        final List<MemberResponse> memberResponses = memberService.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(memberResponses).hasSize(1);
            MemberResponse memberResponse = memberResponses.get(0);
            softly.assertThat(memberResponse.getId()).isEqualTo(1L);
            softly.assertThat(memberResponse.getEmail()).isEqualTo("gavi@woowahan.com");
            softly.assertThat(memberResponse.getPassword()).isEqualTo("1234");
        });
    }

    @Test
    void 아이디와_비밀번호로_회원을_조회한다() {
        // given
        Mockito.when(memberDao.findByEmail(Mockito.any()))
                .thenReturn(Optional.ofNullable(MEMBER_FIXTURE));
        final cart.dto.MemberDto memberDto = new cart.dto.MemberDto("gavi@woowahan.com", "1234");

        // when
        final Member member = memberService.findMember(memberDto);

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getEmail()).isEqualTo("gavi@woowahan.com");
            softly.assertThat(member.getPassword()).isEqualTo("1234");
        });
    }

    @Test
    void 존재하지_않는_회원_조회시_예외가_발생한다() {
        // given
        final MemberDto memberDto = new MemberDto("gavi@woowahan.com", "1234");

        // then
        assertThatThrownBy(() -> memberService.findMember(memberDto))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원 정보가 잘못되었습니다.");
    }
}
