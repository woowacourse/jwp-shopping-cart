package cart.auth;

import cart.domain.Member;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final AuthMemberDao authMemberDao;
    private final BasicAuthorizationParser basicAuthorizationParser;

    public AuthArgumentResolver(
            final AuthMemberDao authMemberDao,
            final BasicAuthorizationParser basicAuthorizationParser
    ) {
        this.authMemberDao = authMemberDao;
        this.basicAuthorizationParser = basicAuthorizationParser;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        final boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Auth.class);
        final boolean hasCredentialType = Credential.class.isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotation && hasCredentialType;
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final String authorizationHeader = webRequest.getHeader(AUTHORIZATION_HEADER_NAME);
        if (authorizationHeader.isBlank() || basicAuthorizationParser.isNotValid(authorizationHeader)) {
            throw new AuthenticationException();
        }

        final Credential credential = basicAuthorizationParser.parse(authorizationHeader);
        final Member member = authMemberDao.findByEmail(credential.getEmail())
                .orElseThrow(AuthenticationException::new);
        validCredential(credential, member);

        return new Credential(member.getId(), credential.getEmail(), credential.getEmail());
    }

    private void validCredential(final Credential credential, final Member member) {
        final String credentialPassword = credential.getPassword();
        final String memberPassword = member.getPassword();
        if (!credentialPassword.equals(memberPassword)) {
            throw new AuthenticationException();
        }
    }
}
