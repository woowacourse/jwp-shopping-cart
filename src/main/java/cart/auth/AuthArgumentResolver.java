package cart.auth;

import cart.auth.dto.AuthenticationDto;
import cart.auth.repository.AuthDao;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthDao authDao;

    public AuthArgumentResolver(final AuthDao authDao) {
        this.authDao = authDao;
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
        return authDao.findIdByEmailAndPassword(authenticationDto.getEmail(), authenticationDto.getPassword());
    }
}
