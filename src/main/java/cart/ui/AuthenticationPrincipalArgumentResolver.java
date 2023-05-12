package cart.ui;

import cart.dto.auth.AuthInfo;
import cart.infrastructure.BasicAuthorizationExtractor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        AuthInfo authInfo = basicAuthorizationExtractor.extract(httpServletRequest);
        if (authInfo == null || authInfo.getEmail() == null) {
            throw new AuthenticationException("로그인이 필요한 서비스입니다.");
        }
        return authInfo;
    }
}
