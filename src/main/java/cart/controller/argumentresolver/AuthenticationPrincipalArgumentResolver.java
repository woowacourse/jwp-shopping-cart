package cart.controller.argumentresolver;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import cart.dto.user.UserRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String DELIMITER = ":";
    private final AuthDecoder authDecoder;

    public AuthenticationPrincipalArgumentResolver(AuthDecoder authDecoder) {
        this.authDecoder = authDecoder;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        final String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String decode = authDecoder.decode(authorization);
        String[] credentials = decode.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        return new UserRequest(email, password);
    }
}
