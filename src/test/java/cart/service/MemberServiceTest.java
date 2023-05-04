package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.member.FakeMemberDao;
import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.dto.member.ResponseMemberDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    @Test
    @DisplayName("display 테스트")
    void display() {
        // given
        final FakeMemberDao fakeMemberDao = new FakeMemberDao();
        final MemberService memberService = new MemberService(fakeMemberDao);
        fakeMemberDao.insert(new Member(1L, new Email("mango@wooteco.com"), new Password("mangopassword")));
        fakeMemberDao.insert(new Member(2L, new Email("dd@wooteco.com"), new Password("ddpassword")));
        // when
        final List<ResponseMemberDto> responseMemberDtos = memberService.display();
        // then
        assertThat(responseMemberDtos.get(0).getEmail()).isEqualTo("mango@wooteco.com");
        assertThat(responseMemberDtos.get(0).getPassword()).isEqualTo("mangopassword");
        assertThat(responseMemberDtos.get(1).getEmail()).isEqualTo("dd@wooteco.com");
        assertThat(responseMemberDtos.get(1).getPassword()).isEqualTo("ddpassword");
    }
}
