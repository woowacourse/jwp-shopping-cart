package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import cart.dao.MemberDao;
import cart.domain.Member;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("AuthInterceptor 은(는)")
class AuthInterceptorTest {

    private final MemberDao memberDao = mock(MemberDao.class);
    private final AuthContext authContext = new AuthContext();
    private final AuthInterceptor authInterceptor = new AuthInterceptor(authContext, memberDao);

    @Test
    void 요청으로부터_회원_정보를_추출하여_저장한다() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Member member = new Member("email@tech.chat", "1234");
        given(memberDao.findByEmail("email@tech.chat"))
                .willReturn(Optional.of(member));

        final byte[] encode = Base64Utils.encode((member.getEmail() + ":" + member.getPassword()).getBytes());
        given(request.getHeader("Authorization"))
                .willReturn("Basic " + new String(encode));

        // when
        final boolean result = authInterceptor.preHandle(request, null, null);

        // then
        assertThat(result).isTrue();
        assertThat(authContext.getAuthMember()).isNotNull();
    }

    @Test
    void 요청에_Authorization_헤더가_없으면_예외() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Member member = new Member("email@tech.chat", "1234");
        given(memberDao.findByEmail("email@tech.chat"))
                .willReturn(Optional.of(member));

        final byte[] encode = Base64Utils.encode((member.getEmail() + ":" + member.getPassword()).getBytes());
        given(request.getHeader("Authorization"))
                .willReturn(null);

        // when
        assertThatThrownBy(() ->
                authInterceptor.preHandle(request, null, null)
        ).isInstanceOf(AuthenticationException.class);

        // then
        assertThat(authContext.getAuthMember()).isNull();
    }

    @Test
    void Authorization_헤더에_담긴_토큰이_Basic_이_아니면_예외() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Member member = new Member("email@tech.chat", "1234");
        given(memberDao.findByEmail("email@tech.chat"))
                .willReturn(Optional.of(member));

        final byte[] encode = Base64Utils.encode((member.getEmail() + ":" + member.getPassword()).getBytes());
        given(request.getHeader("Authorization"))
                .willReturn("NonBasic " + new String(encode));

        // when
        assertThatThrownBy(() ->
                authInterceptor.preHandle(request, null, null)
        ).isInstanceOf(AuthenticationException.class);

        // then
        assertThat(authContext.getAuthMember()).isNull();
    }

    @Test
    void Basic_토큰에_담긴_이메일로_가입한_회원이_없으면_예외() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final Member member = new Member("email@tech.chat", "1234");
        given(memberDao.findByEmail("email@tech.chat"))
                .willReturn(Optional.empty());

        final byte[] encode = Base64Utils.encode((member.getEmail() + ":" + member.getPassword()).getBytes());
        given(request.getHeader("Authorization"))
                .willReturn("NonBasic " + new String(encode));

        // when
        assertThatThrownBy(() ->
                authInterceptor.preHandle(request, null, null)
        ).isInstanceOf(AuthenticationException.class);

        // then
        assertThat(authContext.getAuthMember()).isNull();
    }
}
