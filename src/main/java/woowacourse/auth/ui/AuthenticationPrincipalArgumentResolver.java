package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.exception.TokenExpiredException;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN_ATTRIBUTE_NAME = "Access-Token";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = (String) webRequest.getAttribute(ACCESS_TOKEN_ATTRIBUTE_NAME, NativeWebRequest.SCOPE_REQUEST);
        validateTokenExpired(token);
        final String payload = jwtTokenProvider.getPayload(token);
        validatePayload(payload);

        return Long.parseLong(payload);
    }

    private void validateTokenExpired(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenExpiredException();
        }
    }

    private void validatePayload(String payload) {
        if (payload == null) {
            throw new InvalidTokenException();
        }
    }
}
