package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.domain.member.dto.MemberDto;
import cart.domain.member.entity.Member;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthorizationExtractor authorizationExtractor;
    @Mock
    private MemberDao memberDao;
    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Authentication header 인증 확인한다.")
    public void testCheckAuthenticationHeader() {
        //given
        final MemberDto memberDto = new MemberDto("test@test.com",
            "password");
        final Member member = new Member(1L, memberDto.getEmail(),
            memberDto.getPassword(), null,
            null);
        given(authorizationExtractor.extract(anyString()))
            .willReturn(memberDto);
        given(memberDao.findByEmail(memberDto.getEmail()))
            .willReturn(Optional.of(member));

        //when
        final MemberDto result = authService.checkAuthenticationHeader(anyString());

        //then
        assertThat(result)
            .extracting("email", "password")
            .containsExactly(
                memberDto.getEmail(),
                memberDto.getPassword()
            );
    }

    @Test
    @DisplayName("Authentication header 인증 확인 실패.")
    public void testCheckAuthenticationHeaderFail() {
        //given
        final MemberDto memberDto = new MemberDto("test@test.com",
            "wrongPassword");
        final Member member = new Member(1L, memberDto.getEmail(), "password", null,
            null);
        given(authorizationExtractor.extract(anyString()))
            .willReturn(memberDto);
        given(memberDao.findByEmail(memberDto.getEmail()))
            .willReturn(Optional.of(member));

        //when
        //then
        assertThatThrownBy(() -> authService.checkAuthenticationHeader(anyString()))
            .isInstanceOf(AuthenticationException.class);
    }
}
