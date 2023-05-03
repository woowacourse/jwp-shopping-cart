package cart.auth;

import cart.exception.UserAuthorizationException;
import cart.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final String AUTH_HEADER_KEY = "Authorization";
    private final String BASIC_AUTH_PREFIX = "Basic";
    private final String BASIC_AUTH_HEADER_REGEX = "^" + BASIC_AUTH_PREFIX + " .+$";
    private final String DELIMITER = ":";

    private final UserService userService;

    public AuthArgumentResolver(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final String authHeaderValue = webRequest.getHeader(AUTH_HEADER_KEY);

        if (authHeaderValue == null || !authHeaderValue.matches(BASIC_AUTH_HEADER_REGEX)) {
            System.out.println(authHeaderValue);
            throw new UserAuthorizationException("Basic 인증 형식에 맞지 않습니다.");
        }

        final String authCredential = authHeaderValue.substring(BASIC_AUTH_PREFIX.length()).trim();
        final String decodedCredential = new String(Base64.getDecoder().decode(authCredential));

        final String[] emailPassword = decodedCredential.split(DELIMITER);
        final String email = emailPassword[0];
        final String password = emailPassword[1];

        userService.validateUser(email, password);

        return new AuthUserInfo(email);
    }
}
