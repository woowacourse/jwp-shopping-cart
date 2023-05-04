package cart.config.auth;

import org.springframework.core.MethodParameter;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class Base64AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BASIC = "Basic";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) && parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        final String authValue = webRequest.getHeader(AUTHORIZATION_HEADER);
        final String authValueWithBase64Encoding = authValue.substring(BASIC.length()).trim();
        final String auth = new String(Base64Utils.decodeFromString(authValueWithBase64Encoding));

        final String[] emailAndPasswordWithDecryption = auth.split(":");
        final String email = emailAndPasswordWithDecryption[0];
        return email;
    }
}
