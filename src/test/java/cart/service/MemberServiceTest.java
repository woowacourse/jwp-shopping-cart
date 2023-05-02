package cart.service;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.dto.ResponseMemberDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 사용자를_조회한다() {
        //given
        when(memberDao.findAll())
                .thenReturn(List.of(new MemberEntity(1L, "huchu@woowahan.com", "1234567a!")));

        //when
        final List<ResponseMemberDto> responseMemberDtos = memberService.findAll();

        //then
        assertSoftly(softly -> {
            softly.assertThat(responseMemberDtos).hasSize(1);
            final ResponseMemberDto responseMemberDto = responseMemberDtos.get(0);
            softly.assertThat(responseMemberDto.getId()).isEqualTo(1L);
            softly.assertThat(responseMemberDto.getEmail()).isEqualTo("huchu@woowahan.com");
            softly.assertThat(responseMemberDto.getPassword()).isEqualTo("1234567a!");
        });
    }
}
