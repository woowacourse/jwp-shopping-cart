package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PrincipalResolver implements HandlerMethodArgumentResolver {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final BasicAuthProvider basicAuthProvider;

    public PrincipalResolver(BasicAuthProvider basicAuthProvider) {
        this.basicAuthProvider = basicAuthProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class) && parameter.hasParameterAnnotation(
                Principal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return basicAuthProvider.resolveUser(request.getHeader(AUTHORIZATION_HEADER));
    }
}
