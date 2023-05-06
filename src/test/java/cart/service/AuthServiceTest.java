package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.auth.Credential;
import cart.dto.request.AuthRequest;
import cart.exception.custom.UnauthorizedException;
import cart.persistnece.dao.MemberDao;
import cart.persistnece.entity.Member;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberDao memberDao;

    @DisplayName("정상적으로 credential을 반환한다.")
    @Test
    void find_success() {
        //given
        long memberId = 1L;
        String email = "email@naver.com";
        String password = "password";
        Member expected = new Member(memberId, email, password);
        Optional<Member> member = Optional.of(expected);

        when(memberDao.findByEmail(any()))
                .thenReturn(member);
        //when
        Credential actual = authService.findCredential(new AuthRequest(email, password));
        //then
        assertAll(
                () -> assertThat(actual).usingRecursiveComparison()
                        .ignoringFields("memberId")
                        .isEqualTo(expected),
                () -> assertThat(actual.getMemberId()).isEqualTo(memberId)
        );
    }



    @DisplayName("해당하는 email의 회원이 존재하지 않는다면 예외를 반환한다.")
    @Test
    void throw_by_no_email_matching_member() {
        //given
        when(memberDao.findByEmail(any()))
                .thenReturn(Optional.empty());
        //when && then
        assertThatThrownBy(() -> authService.findCredential(new AuthRequest("email", "password")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("해당하는 email의 회원이 존재하지 않습니다.");
    }

    @DisplayName("회원과 전달받은 password가 일치하지 않으면 예외를 반환한다.")
    @Test
    void throw_by_wrong_password() {
        //given
        String password = "password";
        Optional<Member> member = Optional.of(new Member(1L, "email@naver.com", password));
        when(memberDao.findByEmail(any()))
                .thenReturn(member);
        //when && then
        assertThatThrownBy(() -> authService.findCredential(new AuthRequest("email@naver.com", password +"any")))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("패스워드가 일치하지 않습니다.");
    }
}
