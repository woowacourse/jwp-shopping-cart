package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("login() 메서드 호출시 email이 존재하지 않으면 NoSuchElementException 예외가 발생한다")
    void login_fail1() {
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.login("email", "password"))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("login() 메서드 호출시 비밀번호가 다르면 IllegalArgumentException 예외가 발생한다")
    void login_fail2() {
        MemberEntity result = new MemberEntity(1L, "email", "password");
        given(memberDao.findByEmail(any()))
            .willReturn(Optional.of(result));

        assertThatThrownBy(() -> memberService.login("email", "no"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}