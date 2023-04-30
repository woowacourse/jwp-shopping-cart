package cart.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import cart.exception.AuthenticationException;
import cart.repository.MemberDao;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Base64Utils;

@ExtendWith(MockitoExtension.class)
class BasicAuthProviderTest {
    @Mock
    MemberDao memberDao;

    @InjectMocks
    BasicAuthProvider basicAuthProvider;

    @Test
    @DisplayName("Basic 형식의 토큰이 입력되면 정상적으로 사용자의 정보가 반환되어야 한다.")
    void resolveUser_success() {
        // given
        String token = "Basic Z2xlbmZpZGRpY2hAbmF2ZXIuY29tOjEyMzQ1Ng==";
        given(memberDao.findByEmailAndPassword("glenfiddich@naver.com", "123456"))
                .willReturn(Optional.of(1L));

        // when
        User user = basicAuthProvider.resolveUser(token);

        // then
        assertThat(user.getMemberId())
                .isEqualTo(1L);
        assertThat(user.getEmail())
                .isEqualTo("glenfiddich@naver.com");
        assertThat(user.getPassword())
                .isEqualTo("123456");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "Base", "basic"})
    @DisplayName("Basic 형식의 토큰이 아니면 예외가 발생해야 한다.")
    void resolveMemberId_invalidBasicToken(String input) {
        // given
        String token = input + " Z2xlbmZpZGRpY2hAbmF2ZXIuY29tOjEyMzQ1Ng==";

        // expect
        assertThatThrownBy(() -> basicAuthProvider.resolveUser(token))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("베이직 형식의 토큰이 아닙니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"glen@naver.com:1234:glen", "glen@naver.com,1234"})
    @DisplayName("토큰에 :가 2개 이상이거나 없으면 예외가 발생해야 한다.")
    void resolveMemberId_invalidDelimiter(String input) {
        // given
        String token = Base64Utils.encodeToString(input.getBytes(StandardCharsets.UTF_8));

        // expect
        assertThatThrownBy(() -> basicAuthProvider.resolveUser("Basic " + token))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("올바른 형식의 토큰이 아닙니다.");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("인증 토큰이 비어있으면 예외가 발생해야 한다.")
    void resolveMemberId_blankToken(String token) {
        // expect
        assertThatThrownBy(() -> basicAuthProvider.resolveUser(token))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage("인증 토큰이 비어있습니다.");
    }
}
