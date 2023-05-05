package cart.service;

import cart.dao.MemberDao;
import cart.dto.MemberResponseDto;
import cart.entity.Member;
import cart.vo.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static cart.vo.Email.from;
import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    private MemberService memberService;
    private MemberDao memberDao;

    @BeforeEach
    void beforeEach() {
        memberDao = Mockito.mock(MemberDao.class);
        memberService = new MemberService(memberDao);
    }

    @Test
    @DisplayName("사용자의 정보들을 모두 불러오는 기능을 테스트한다")
    void findAll() {
        BDDMockito.given(memberDao.selectAll())
                .willReturn(new ArrayList<>(
                        List.of(
                                getMember("kpeel5839@a.com", "password1!"),
                                getMember("jakind@b.com", "password2!"))
                        )
                );

        List<MemberResponseDto> members = memberService.findAll();

        assertThat(members).hasSize(2)
                .anyMatch(memberResponseDto -> memberResponseDto.getEmail().equals("kpeel5839@a.com"))
                .anyMatch(memberResponseDto -> memberResponseDto.getEmail().equals("jakind@b.com"));
    }

    private Member getMember(String email, String password) {
        return new Member.Builder()
                .email(from(email))
                .password(Password.from(password))
                .build();
    }

}
