package cart.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.dto.AuthInfo;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
        final AuthInfo authInfo = new AuthInfo("test@test.com", "password");
        final Member member = new Member(1L, authInfo.getEmail(), authInfo.getPassword(), null,
            null);
        given(authorizationExtractor.extract(anyString()))
            .willReturn(authInfo);
        given(memberDao.findByEmail(authInfo.getEmail()))
            .willReturn(Optional.of(member));

        //when
        boolean result = authService.checkAuthenticationHeader(anyString());

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Authentication header 인증 확인 실패.")
    public void testCheckAuthenticationHeaderFail() {
        //given
        final AuthInfo authInfo = new AuthInfo("test@test.com", "wrongPassword");
        final Member member = new Member(1L, authInfo.getEmail(), "password", null,
            null);
        given(authorizationExtractor.extract(anyString()))
            .willReturn(authInfo);
        given(memberDao.findByEmail(authInfo.getEmail()))
            .willReturn(Optional.of(member));

        //when
        boolean result = authService.checkAuthenticationHeader(anyString());

        //then
        assertThat(result).isFalse();
    }
}
