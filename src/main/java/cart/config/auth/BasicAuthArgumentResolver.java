package cart.config.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_BASIC = "Basic ";
    private static final int EMAIL = 0;
    private static final int PASSWORD = 1;

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

        return parseToken(basicToken);
    }

    private void validate(final String basicToken) {
        if (Objects.isNull(basicToken)) {
            throw new BasicAuthException("토큰이 존재하지 않습니다.");
        }
        if (!basicToken.startsWith(AUTHORIZATION_BASIC)) {
            throw new BasicAuthException("인증이 불가능한 토큰입니다.");
        }
    }

    private AuthMember parseToken(final String basicToken) {
        final String token = basicToken.replaceFirst(AUTHORIZATION_BASIC, "");
        final String memberInformation = new String(Base64Utils.decodeFromString(token));

        final String[] memberInformations = memberInformation.split(":");
        final String email = memberInformations[EMAIL];
        final String password = memberInformations[PASSWORD];

        return authService.login(email, password);
    }
}
