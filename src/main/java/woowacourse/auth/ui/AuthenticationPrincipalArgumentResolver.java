package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.FindCustomerRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        String token = AuthorizationExtractor.extractFromString(authorization);
        if (authService.validateToken(token)) {
            String payload = authService.getPayload(token);
            return new FindCustomerRequest(payload);
        }
        throw new InvalidTokenException();
    }
}
