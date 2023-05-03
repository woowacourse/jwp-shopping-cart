package cart.auth;

import cart.dao.JdbcMemberDao;
import cart.dto.MemberDto;
import cart.entity.MemberEntity;
import cart.exception.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationPrincipalServiceTest {
    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    JdbcMemberDao memberDao;

    @Nested
    @DisplayName("사용자를 인증하는 login 메서드 테스트")
    class LoginTest {

        @DisplayName("사용자가 존재할 경우 사용자를 반환하는지 확인한다")
        @Test
        void successTest() {
            final MemberAuthentication memberAuthentication = MemberAuthentication.of("irene@email.com", "password1");
            when(memberDao.selectByEmailAndPassword(any())).thenReturn(MemberEntity.of(1L, "irene@email.com", "password1"));

            final MemberDto login = authenticationService.login(memberAuthentication);

            assertAll(
                    () -> assertThat(login.getId()).isEqualTo(1L),
                    () -> assertThat(login.getEmail()).isEqualTo("irene@email.com"),
                    () -> assertThat(login.getPassword()).isEqualTo("password1")
            );
        }

        @DisplayName("사용자가 존재하지 않을 경우 예외를 반환하는지 확인한다")
        @Test
        void failTest() {
            final MemberAuthentication memberAuthentication = MemberAuthentication.of("hi@email.com", "password1");
            when(memberDao.selectByEmailAndPassword(any())).thenReturn(null);

            assertThatThrownBy(() -> authenticationService.login(memberAuthentication))
                    .isInstanceOf(AuthenticationException.class);

        }
    }

}
