package cart.config.auth;

import cart.common.auth.AuthInfo;
import cart.common.auth.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_BASIC = "Basic ";

    private final AuthService authService;

    public BasicAuthArgumentResolver(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuth.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String basicToken = webRequest.getHeader(HEADER_AUTHORIZATION);
        validate(basicToken);

        final AuthInfo authInfo = new BasicAuthProvider().getAuthInfo(basicToken);

        return authService.login(authInfo.getEmail(), authInfo.getPassword());
    }

    private void validate(final String basicToken) {
        if (Objects.isNull(basicToken)) {
            throw new BasicAuthException("토큰이 존재하지 않습니다.");
        }
        if (!basicToken.startsWith(AUTHORIZATION_BASIC)) {
            throw new BasicAuthException("인증이 불가능한 토큰입니다.");
        }
    }
}
