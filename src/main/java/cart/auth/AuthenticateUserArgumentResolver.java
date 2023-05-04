package cart.auth;

import cart.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticateUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final BasicAuthExtractor basicAuthExtractor;
    private final UserService userService;

    public AuthenticateUserArgumentResolver(BasicAuthExtractor basicAuthExtractor, UserService userService) {
        this.basicAuthExtractor = basicAuthExtractor;
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticateUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final AuthHeader authorization = new AuthHeader(request.getHeader(AUTHORIZATION));
        final AuthInfo authInfo = basicAuthExtractor.extract(authorization);

        return userService.findByAuthPrincipal(authInfo.getEmail(), authInfo.getPassword());
    }
}
