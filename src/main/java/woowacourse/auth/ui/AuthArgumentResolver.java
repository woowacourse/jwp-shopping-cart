package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.domain.User;
import woowacourse.auth.support.AuthenticatedUser;
import woowacourse.auth.support.HttpHeaderConstant;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AuthArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUser.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return authService.findUserByToken(extractBearerToken(request));
    }

    public String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaderConstant.AUTHORIZATION);
        String bearerToken = authorizationHeader.substring(HttpHeaderConstant.BEARER_TYPE.length()).trim();
        return toSingleToken(bearerToken);
    }

    private String toSingleToken(String bearerToken) {
        int commaIndex = bearerToken.indexOf(',');
        if (commaIndex > 0) {
            return bearerToken.substring(0, commaIndex);
        }
        return bearerToken;
    }
}
