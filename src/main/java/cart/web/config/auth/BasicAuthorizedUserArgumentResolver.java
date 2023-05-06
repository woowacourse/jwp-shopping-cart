package cart.web.config.auth;

import cart.domain.user.service.BasicAuthorizationCartUserService;
import cart.web.dto.request.AuthorizedUserRequest;
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
    private final BasicAuthorizationCartUserService basicAuthorizationCartUserService;

    public BasicAuthorizedUserArgumentResolver(
            final AuthCredentialDecoder<AuthorizedUserRequest> authCredentialDecoder,
            final BasicAuthorizationCartUserService basicAuthorizationCartUserService
    ) {
        this.authCredentialDecoder = authCredentialDecoder;
        this.basicAuthorizationCartUserService = basicAuthorizationCartUserService;
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
    ) {
        final String credentialValue = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final AuthorizedUserRequest authorizedUserRequest = authCredentialDecoder.decodeCredential(credentialValue);

        basicAuthorizationCartUserService.verifyCartUser(authorizedUserRequest.getEmail(),
                authorizedUserRequest.getPassword());

        return authorizedUserRequest;
    }
}
