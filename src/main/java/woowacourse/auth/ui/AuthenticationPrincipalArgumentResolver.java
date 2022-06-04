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
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider tokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    public AuthenticationPrincipalArgumentResolver(JwtTokenProvider tokenProvider,
        AuthorizationExtractor authorizationExtractor) {
        this.tokenProvider = tokenProvider;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = authorizationExtractor.extract((HttpServletRequest)webRequest.getNativeRequest());
        String payload = tokenProvider.getPayload(token);
        return new FindCustomerRequest(payload);
    }
}
