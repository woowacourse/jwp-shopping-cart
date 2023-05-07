package cart.authentication;

import cart.controller.dto.AuthInfo;
import cart.exception.auth.NotSignInException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthInfoThreadLocal authInfoThreadLocal;

    public AuthenticationPrincipalArgumentResolver(AuthInfoThreadLocal authInfoThreadLocal) {
        this.authInfoThreadLocal = authInfoThreadLocal;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class)
                && parameter.getParameterType().equals(AuthInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        AuthInfo authInfo = authInfoThreadLocal.get();

        if (authInfo == null) {
            throw new NotSignInException("로그인이 필요한 기능입니다.");
        }
        return authInfo;
    }
}
