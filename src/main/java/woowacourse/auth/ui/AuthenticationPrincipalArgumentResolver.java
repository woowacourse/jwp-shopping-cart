package woowacourse.auth.ui;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.UnauthorizedException;

import java.util.Objects;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER = "BEARER";
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    // parameter 에 @AuthenticationPrincipal 이 붙어있는 경우 동작
    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String auth = webRequest.getHeader("Authorization");
        if (auth == null) {
            throw new UnauthorizedException();
        }
        final String token = Objects.requireNonNull(auth).substring(BEARER.length() + 1);
        final String payload = jwtTokenProvider.getPayload(token);
        if (payload == null) {
            throw new MalformedJwtException("잘못된 형식의 토큰입니다.");
        }

        return Long.parseLong(payload);
    }
}
