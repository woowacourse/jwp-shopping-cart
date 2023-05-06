package cart.config;

import cart.annotation.Auth;
import cart.dto.request.AuthRequest;
import cart.dto.request.Credential;
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
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private final AuthService authService;

    public AuthArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) &&
                parameter.getParameterType().equals(Credential.class);
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

        AuthRequest authRequest = decodeByBase64(authorization);
        return authService.findCredential(authRequest);
    }

    private AuthRequest decodeByBase64(String authorization) {
        String encodedValue = authorization.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String[] authValues = new String(Base64Utils.decodeFromString(encodedValue)).split(":");
        return new AuthRequest(authValues);
    }
}
