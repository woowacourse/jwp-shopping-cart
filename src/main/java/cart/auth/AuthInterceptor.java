package cart.auth;

import cart.domain.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final AuthMemberDao authMemberDao;
    private final BasicAuthorizationParser basicAuthorizationParser;
    private final CredentialThreadLocal credentialThreadLocal;

    public AuthInterceptor(
            final AuthMemberDao authMemberDao,
            final BasicAuthorizationParser basicAuthorizationParser,
            final CredentialThreadLocal credentialThreadLocal
    ) {
        this.authMemberDao = authMemberDao;
        this.basicAuthorizationParser = basicAuthorizationParser;
        this.credentialThreadLocal = credentialThreadLocal;
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorizationHeader.isBlank() || basicAuthorizationParser.isNotValid(authorizationHeader)) {
            throw new AuthenticationException();
        }

        final Credential credential = basicAuthorizationParser.parse(authorizationHeader);
        final Member member = authMemberDao.findByEmail(credential.getEmail())
                .orElseThrow(AuthenticationException::new);
        validCredential(credential, member);

        credentialThreadLocal.set(new Credential(member.getId(), credential.getEmail(), credential.getEmail()));
        return true;
    }

    private void validCredential(final Credential credential, final Member member) {
        final String credentialPassword = credential.getPassword();
        final String memberPassword = member.getPassword();
        if (!credentialPassword.equals(memberPassword)) {
            throw new AuthenticationException();
        }
    }
}
