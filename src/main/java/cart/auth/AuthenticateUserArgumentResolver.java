package cart.auth;

import cart.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthenticateUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticateService authenticateService;
    private final UserService userService;

    public AuthenticateUserArgumentResolver(AuthenticateService authenticateService, UserService userService) {
        this.authenticateService = authenticateService;
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticateUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        final AuthInfo authInfo = authenticateService.extract(request);

        return userService.findByAuthPrincipal(authInfo.getEmail(), authInfo.getPassword());
    }
}
