package cart.service;

import static cart.service.MemberServiceTest.MEMBER_FIXTURE;
import static org.assertj.core.api.Assertions.assertThat;

import cart.auth.AuthDto;
import cart.dao.MemberDao;
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
class AuthServiceTest {

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private AuthService authService;
    @Test
    void 회원정보가_일치하면_True를_반환한다() {
        // given
        Mockito.when(memberDao.findByEmailAndPassword(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        final String email = "gavi@woowahan.com";
        final String password =  "1234";

        // expect
        assertThat(authService.isInvalidAuth(email, password)).isTrue();
    }

    @Test
    void 회원정보가_일치하지_않으면_False를_반환한다() {
        // given
        Mockito.when(memberDao.findByEmailAndPassword(Mockito.any(), Mockito.any()))
                .thenReturn(Optional.ofNullable(MEMBER_FIXTURE));
        final String email = "gavi@woowahan.com";
        final String password =  "1234";

        // expect
        assertThat(authService.isInvalidAuth(email, password)).isFalse();
    }
}
