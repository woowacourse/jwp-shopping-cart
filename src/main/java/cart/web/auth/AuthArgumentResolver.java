package cart.web.auth;

import cart.exception.AuthorizationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final int INDEX_OF_EMAIL = 0;
    private static final int INDEX_OF_PASSWORD = 1;
    private static final int SIZE_OF_CREDENTIAL = 2;
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(Auth.class)) {
            Auth authAnnotation = parameter.getParameterAnnotation(Auth.class);

            return authAnnotation != null && authAnnotation.required();
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        BasicAuthorizationExtractor extractor = BasicAuthorizationExtractor.getInstance();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String decodedAuthorization = extractor.extract(request);

        return createUserInfo(decodedAuthorization);
    }

    private UserInfo createUserInfo(String authInfo) {
        String[] credentials = authInfo.split(DELIMITER);

        if (credentials.length != SIZE_OF_CREDENTIAL) {
            throw new AuthorizationException();
        }

        String email = credentials[INDEX_OF_EMAIL];
        String password = credentials[INDEX_OF_PASSWORD];

        return new UserInfo(email, password);
    }
}
