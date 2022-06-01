package woowacourse.auth.ui;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.TokenExpiredException;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String token = (String) webRequest.getAttribute(ACCESS_TOKEN, NativeWebRequest.SCOPE_REQUEST);
        validateTokenExpired(token);
        final String payload = jwtTokenProvider.getPayload(token);
        validatePayload(payload);

        return Long.parseLong(payload);
    }

    private void validateTokenExpired(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new TokenExpiredException();
        }
    }

    private void validatePayload(final String payload) {
        if (payload == null) {
            throw new MalformedJwtException("잘못된 형식의 토큰입니다.");
        }
    }
}
