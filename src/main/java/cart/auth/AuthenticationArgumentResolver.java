package cart.auth;

import cart.service.CustomerService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private final CustomerService customerService;
    private final AuthService authService;

    public AuthenticationArgumentResolver(final CustomerService customerService, final AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws UnauthorizedException {
        String authHeader = webRequest.getHeader(AUTHORIZATION);
        String email = authService.resolveAuthInfo(authHeader).getEmail();
        return customerService.findIdByEmail(email);
    }
}
