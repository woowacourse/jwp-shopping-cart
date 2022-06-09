package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.InvalidTokenException;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

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
        String token = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));

        System.out.println("test");

        if (token == null || token.isEmpty()) {
            //throw new InvalidTokenException("토큰 정보가 존재하지 않습니다.");
            return 0L;
        }

        if (!jwtTokenProvider.validateToken(token)) {
            //throw new InvalidTokenException("유효하지 않거나 만료된 토큰입니다.");
            return 0L;
        }

        return Long.valueOf(jwtTokenProvider.getPayload(token));
    }
}
