package cart.service;

import cart.dto.cart.UserDto;
import cart.entity.MemberEntity;
import cart.exception.AuthorizationException;
import cart.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;
    @Mock
    MemberRepository memberRepository;

    @Test
    @DisplayName("email에 해당하는 회원을 찾는다")
    void findMemberByEmailTest_success() {
        String email = "a@a.com";
        MemberEntity memberEntity = new MemberEntity(1L, email, "1234");
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(memberEntity));

        UserDto expectDto = UserDto.fromMemberEntity(memberEntity);
        assertThat(authService.findMemberByEmail(email)).isEqualTo(expectDto);
    }

    @Test
    @DisplayName("email에 해당하는 회원이 없으면 에외를 반환한다.")
    void findMemberByEmailTest_fail() {
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.findMemberByEmail("email@email.com"))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("회원을 찾을 수 없습니다.");
    }
}