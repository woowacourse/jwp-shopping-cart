package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.TokenProvider;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.exception.NoAuthorizationHeaderException;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;

    public AuthenticationPrincipalArgumentResolver(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new NoAuthorizationHeaderException();
        }
        String accessToken = AuthorizationExtractor.extract(authorizationHeader);

        if (!tokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }

        String username = tokenProvider.getPayload(accessToken);
        return new LoginCustomer(username);
    }
}
