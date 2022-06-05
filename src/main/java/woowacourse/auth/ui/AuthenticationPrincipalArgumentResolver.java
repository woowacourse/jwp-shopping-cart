package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.common.exception.AuthException;
import woowacourse.common.exception.dto.ErrorResponse;

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
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        final HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        checkNativeRequest(nativeRequest);

        final String token = AuthorizationExtractor.extract(nativeRequest);
        checkValidateToken(token);

        return jwtTokenProvider.getPayload(token);
    }

    private void checkNativeRequest(HttpServletRequest nativeRequest) {
        if (nativeRequest == null) {
            throw new AuthException("유효하지 않은 토큰입니다.", ErrorResponse.INVALID_TOKEN);
        }
    }

    private void checkValidateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthException("유효하지 않은 토큰입니다.", ErrorResponse.INVALID_TOKEN);
        }
    }
}
