package woowacourse.shoppingcart.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.shoppingcart.auth.support.jwt.AuthenticationPrincipal;
import woowacourse.shoppingcart.auth.support.jwt.AuthorizationExtractor;
import woowacourse.shoppingcart.auth.support.jwt.JwtTokenProvider;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = AuthorizationExtractor.extract(request)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION));
        return extractIdFromToken(token);
    }

    private Long extractIdFromToken(final String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthException(AuthExceptionCode.EXPIRED_TOKEN);
        }
        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }
}
