package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationContext;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.customer.Email;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationContext authenticationContext;

    public AuthenticationPrincipalArgumentResolver(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Email resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                 NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return authenticationContext.getEmail();
    }
}
