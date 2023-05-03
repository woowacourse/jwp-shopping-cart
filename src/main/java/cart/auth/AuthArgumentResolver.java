package cart.auth;

import cart.auth.service.AuthService;
import cart.auth.dto.AuthenticationDto;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static cart.auth.AuthConstant.AUTHORIZATION;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AuthArgumentResolver(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(Integer.class) &&
                parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
        final AuthenticationDto authenticationDto = BasicAuthorizationExtractor.extract(authorizationHeader);
        return authService.getUserId(authenticationDto);
    }
}
