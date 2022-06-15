package woowacourse.auth.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.service.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.domain.customer.UserName;

public class OptionalUserNameResolver implements HandlerMethodArgumentResolver {

    private AuthService authService;

    public OptionalUserNameResolver(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(woowacourse.auth.support.OptionalUserNameResolver.class);
    }

    @Override
    public Optional<UserName> resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                              final NativeWebRequest webRequest,
                                              final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        try {
            final String token = AuthorizationExtractor.extractOrThrow(request);
            authService.validateToken(token);
            return Optional.of(authService.getUserNameFormToken(token));
        } catch (AuthorizationException e) {
            return Optional.empty();
        }
    }
}
