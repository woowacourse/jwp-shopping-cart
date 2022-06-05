package woowacourse.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Long resolveArgument(final MethodParameter parameter,
                                final ModelAndViewContainer mavContainer,
                                final NativeWebRequest webRequest,
                                final WebDataBinderFactory binderFactory) {
        final HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        final String token = AuthorizationExtractor.extract(Objects.requireNonNull(httpServletRequest));

        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }
}
