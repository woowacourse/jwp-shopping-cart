package cart.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader("Authorization");
        String basicHeaderValue = header.substring("Basic".length()).trim();
        byte[] decodedHeaderValue = Base64.decodeBase64(basicHeaderValue);
        String decodedStringHeaderValue = new String(decodedHeaderValue);

        String[] splitDecodedStringHeaderValue = decodedStringHeaderValue.split(DELIMITER);
        int id = Integer.parseInt(splitDecodedStringHeaderValue[0]);
        String email = splitDecodedStringHeaderValue[1];
        String password = splitDecodedStringHeaderValue[2];

        return new AuthMember(id, email, password);
    }

}
