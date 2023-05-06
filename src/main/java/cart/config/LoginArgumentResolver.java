package cart.config;

import cart.annotation.Login;
import cart.domain.Member;
import cart.exception.custom.UnauthorizedException;
import cart.service.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private final AuthService authService;

    public LoginArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class) &&
                parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            throw new UnauthorizedException(String.format("%s Header가 존재하지 않습니다.", AUTHORIZATION_HEADER));
        }


        if (!authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new UnauthorizedException(
                    String.format("Authorization Header는 '%s'로 시작해야 합니다.", AUTHORIZATION_HEADER_PREFIX));
        }

        String[] credentials = decodeByBase64(authorization);

        String email = credentials[0];
        String password = credentials[1];
        return authService.findAuthInfo(email, password);
    }

    private String[] decodeByBase64(String authorization) {
        String encodedValue = authorization.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        return new String(Base64Utils.decodeFromString(encodedValue)).split(":");
    }
}
