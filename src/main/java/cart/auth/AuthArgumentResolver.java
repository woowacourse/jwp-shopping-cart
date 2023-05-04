package cart.auth;

import cart.exception.BasicAuthException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        String authHeader = webRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new BasicAuthException("사용자 인증 정보가 존재하지 않습니다.");
        }

        String basicToken = authHeader.substring(BASIC_TYPE.length()).trim();
        byte[] bytes = Base64.decodeBase64(basicToken);
        String[] auth = new String(bytes).split(":");

        return new AuthInfo(auth[0], auth[1]);
    }
}
