package cart.service;

import cart.dao.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static cart.fixture.MemberFixture.SECOND_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(MemberService.class)
public class MemberServiceTest {
    @MockBean
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

    @Test
    void 모든_사용자_목록을_가져온다() {
        given(memberDao.findAll()).willReturn(List.of(FIRST_MEMBER.MEMBER_WITH_ID, SECOND_MEMBER.MEMBER_WITH_ID));
        assertThat(memberService.findAll()).usingRecursiveComparison().isEqualTo(List.of(FIRST_MEMBER.RESPONSE, SECOND_MEMBER.RESPONSE));
    }

    @Test
    void 사용자를_저장한다() {
        assertThatNoException()
                .isThrownBy(() -> memberService.save(FIRST_MEMBER.REQUEST));
    }
}
