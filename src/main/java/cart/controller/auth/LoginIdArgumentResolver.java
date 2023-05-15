package cart.controller.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthRequest authRequest;

    public LoginIdArgumentResolver(final AuthRequest authRequest) {
        this.authRequest = authRequest;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginId.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {

        final Long id = authRequest.getId();
        if (id == null) {
            throw new LoginBindingException();
        }
        return id;
    }

}
