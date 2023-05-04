package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.AuthDto;
import cart.dto.request.CreateMemberRequest;
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

    public static final MemberEntity MEMBER_ENTITY_FIXTURE = new MemberEntity(1L, "gavi@woowahan.com", "1234");

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 모든_회원을_조회한다() {
        //given
        Mockito.when(memberDao.findAll())
                .thenReturn(Optional.ofNullable(List.of(MEMBER_ENTITY_FIXTURE)));

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
    void 회원을_생성한다() {
        //given
        Mockito.when(memberDao.findMemberByEmail(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(memberDao.insert(Mockito.any()))
                .thenReturn(1);
        final CreateMemberRequest createMemberRequest = new CreateMemberRequest("gavi@woowahan.com", "password");

        //when
        final int affectedRows = memberService.create(createMemberRequest);

        //then
        assertThat(affectedRows).isOne();
    }

    @Test
    void 존재하는_이메일을_추가할_수_없다() {
        // given
        Mockito.when(memberDao.findMemberByEmail(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(MEMBER_ENTITY_FIXTURE));
        final CreateMemberRequest createMemberRequest = new CreateMemberRequest("gavi@woowahan.com", "password");

        // expect
        assertThatThrownBy(() -> memberService.create(createMemberRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }


    @Test
    void 아이디와_비밀번호로_회원을_조회한다() {
        // given
        Mockito.when(memberDao.findMember(Mockito.any()))
                .thenReturn(Optional.ofNullable(MEMBER_ENTITY_FIXTURE));
        final AuthDto authDto = new AuthDto("gavi@woowahan.com", "1234");

        // when
        final MemberEntity memberEntity = memberService.findMember(authDto);

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberEntity.getEmail()).isEqualTo("gavi@woowahan.com");
            softly.assertThat(memberEntity.getPassword()).isEqualTo("1234");
        });
    }

    @Test
    void 존재하지_않는_회원_조회시_예외가_발생한다() {
        // given
        final AuthDto authDto = new AuthDto("gavi@woowahan.com", "1234");

        // then
        assertThatThrownBy(() -> memberService.findMember(authDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원 정보가 잘못되었습니다.");
    }
}
