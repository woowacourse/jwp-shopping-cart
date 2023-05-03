package cart.controller;

import cart.controller.dto.LoginUser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class BasicAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuthorization.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        String authorizationHeader = webRequest.getHeader("authorization");
        assert authorizationHeader != null;
        if (!authorizationHeader.toLowerCase().startsWith("basic")) {
            throw new IllegalArgumentException("잘못된 인증입니다.");
        }
        String credentials = authorizationHeader.split("\\s")[1];
        byte[] bytes = Base64.decodeBase64(credentials);
        String[] emailAndPassword = new String(bytes).split(":");
        String email = emailAndPassword[0];
        String password = emailAndPassword[1];
        return new LoginUser(email, password);
    }
}
