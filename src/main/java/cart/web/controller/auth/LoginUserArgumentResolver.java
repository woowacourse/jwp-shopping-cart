package cart.web.controller.auth;

import cart.domain.user.User;
import cart.domain.user.UserEmail;
import cart.web.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public LoginUserArgumentResolver(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUser.class);
    }

    @Override
    public UserEmail resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                     final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final User user = BasicAuthorizationExtractor.extract(request);
        
        return new UserEmail(user.getUserEmailValue());
    }
}
