package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.dto.MemberResponseDto;
import cart.entity.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MemberServiceTest {

    private static final String EMAIL = "email1@email.com";
    private static final String PASSWORD = "email";

    private MemberService memberService;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        memberDao = Mockito.mock(MemberDao.class);
        memberService = new MemberService(memberDao);
    }

    @Test
    @DisplayName("사용자를 모두 조회한다.")
    void findAll() {
        given(memberDao.findAll())
                .willReturn(List.of(new Member.Builder()
                        .email("email1@email.com")
                        .password("email1")
                        .build()));

        List<MemberResponseDto> all = memberService.findAll();

        assertAll(
                () -> assertThat(all.size()).isEqualTo(1),
                () -> assertThat(all.get(0).getEmail()).isEqualTo("email1@email.com"),
                () -> assertThat(all.get(0).getPassword()).isEqualTo("email1")
        );
    }

    @Test
    @DisplayName("존재하는 사용자이면 true 를 반환한다.")
    void isExistMember_true() {
        given(memberDao.findByEmail(EMAIL))
                .willReturn(Optional.of(new Member.Builder()
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build()));

        assertThat(memberService.isExistMember(EMAIL)).isTrue();
    }

    @Test
    @DisplayName("존재하는 사용자가 아니면 false 를 반환한다.")
    void isExistMember_false() {
        given(memberDao.findByEmail(EMAIL))
                .willReturn(Optional.empty());

        assertThat(memberService.isExistMember(EMAIL)).isFalse();
    }

}
