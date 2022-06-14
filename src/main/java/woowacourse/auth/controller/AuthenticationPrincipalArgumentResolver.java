package woowacourse.auth.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.LoginCustomer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    public AuthenticationPrincipalArgumentResolver(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public LoginCustomer resolveArgument(final MethodParameter parameter,
                                         final ModelAndViewContainer mavContainer,
                                         final NativeWebRequest webRequest,
                                         final WebDataBinderFactory binderFactory) {
        final String token = (String) webRequest.getAttribute("token", RequestAttributes.SCOPE_REQUEST);
        final String email = authService.extractEmail(token);
        return new LoginCustomer(email);
    }
}
