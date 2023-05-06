package cart.auth;

import cart.annotation.Auth;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final CredentialThreadLocal credentialThreadLocal;

    public AuthArgumentResolver(CredentialThreadLocal credentialThreadLocal) {
        this.credentialThreadLocal = credentialThreadLocal;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) &&
                parameter.getParameterType().equals(Credential.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Credential credential = credentialThreadLocal.get();
        credentialThreadLocal.clear();
        return credential;
    }
}
