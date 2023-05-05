package cart.web.controller.auth;

import cart.domain.user.User;
import cart.exception.UnAuthorizedException;
import cart.web.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public LoginUserArgumentResolver(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUser.class);
//        final boolean hasUserType = User.class.isAssignableFrom(parameter.getParameterType());
//        return hasLoginAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final User user = BasicAuthorizationExtractor.extract(request);
        final String userEmailValue = Objects.requireNonNull(user).getUserEmailValue();
        final String userPasswordValue = Objects.requireNonNull(user.getUserPasswordValue());
        final Optional<User> userOptional = userService.findUserByEmailAndPassword(userEmailValue, userPasswordValue);

        return userOptional.orElseThrow(UnAuthorizedException::new);
    }
}
