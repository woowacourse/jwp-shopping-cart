package cart.web.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Auth.class)) {
            Auth authAnnotation = parameter.getParameterAnnotation(Auth.class);

            return authAnnotation.required();
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        AuthorizationExtractor<UserInfo> extractor = basicAuthorizationExtractor();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        return extractor.extract(request);
    }

    @Bean
    private AuthorizationExtractor<UserInfo> basicAuthorizationExtractor() {
        return new BasicAuthorizationExtractor();
    }
}
