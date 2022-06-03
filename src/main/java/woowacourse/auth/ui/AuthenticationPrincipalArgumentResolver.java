package woowacourse.auth.ui;

import static woowacourse.auth.support.AuthorizationExtractor.AUTHORIZATION;
import static woowacourse.auth.support.AuthorizationExtractor.BEARER_TYPE;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.AuthorizationException;

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
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String auth = webRequest.getHeader(AUTHORIZATION);
        String token = getToken(auth);
        return jwtTokenProvider.getPayload(token);
    }

    private String getToken(String auth) {
        if (auth == null || auth.isBlank()) {
            throw new AuthorizationException("토큰이 존재하지 않습니다.");
        }
        return auth.substring(BEARER_TYPE.length()).trim();
    }
}
