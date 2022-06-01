package woowacourse.auth.ui;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = (String) webRequest.getAttribute(ACCESS_TOKEN, NativeWebRequest.SCOPE_REQUEST);
        final String payload = jwtTokenProvider.getPayload(token);
        if (payload == null) {
            throw new MalformedJwtException("잘못된 형식의 토큰입니다.");
        }

        return Long.parseLong(payload);
    }
}
