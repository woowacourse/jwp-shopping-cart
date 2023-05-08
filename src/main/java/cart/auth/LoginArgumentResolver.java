package cart.auth;

import cart.auth.dto.BasicAuthInfo;
import cart.auth.extractor.BasicAuthorizationExtractor;
import cart.domain.member.Member;
import cart.service.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public LoginArgumentResolver(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class) && parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        BasicAuthInfo authInfo = BasicAuthorizationExtractor.extract(header);
        return authService.login(authInfo.getEmail(), authInfo.getPassword());
    }
}
