package cart.web.config.auth;

import cart.domain.user.service.UserAuthorizationVerifier;
import cart.web.controller.dto.request.AuthorizedUserRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class BasicAuthorizedUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthCredentialDecoder<AuthorizedUserRequest> authCredentialDecoder;
    private final UserAuthorizationVerifier userAuthorizationVerifier;

    public BasicAuthorizedUserArgumentResolver(
            final AuthCredentialDecoder<AuthorizedUserRequest> authCredentialDecoder,
            final UserAuthorizationVerifier userAuthorizationVerifier
    ) {
        this.authCredentialDecoder = authCredentialDecoder;
        this.userAuthorizationVerifier = userAuthorizationVerifier;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUser.class);
    }

    @Override
    public AuthorizedUserRequest resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String credentialValue = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final AuthorizedUserRequest authorizedUserRequest = authCredentialDecoder.decodeCredential(credentialValue);

        userAuthorizationVerifier.verifyCartUser(authorizedUserRequest.getEmail(),
                authorizedUserRequest.getPassword());

        return authorizedUserRequest;
    }
    //TODO: 얜 테스트 어떻게 하지
}
