package cart.service;

import static org.mockito.BDDMockito.given;

import cart.dto.MemberDto;
import cart.repository.dao.MemberDao;
import cart.repository.entity.MemberEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDao memberDao;

    @Test
    void 모든_사용자를_조회한다() {
        final MemberEntity memberEntity = new MemberEntity(1L, "name", "email", "password");
        given(memberDao.findAll()).willReturn(List.of(memberEntity));

        final List<MemberDto> memberDtos = memberService.findAllMember();

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(memberDtos.size()).isOne();
        softAssertions.assertThat(memberDtos.get(0).getName()).isEqualTo("name");
        softAssertions.assertThat(memberDtos.get(0).getEmail()).isEqualTo("email");
        softAssertions.assertThat(memberDtos.get(0).getPassword()).isEqualTo("password");
        softAssertions.assertAll();
    }
}
