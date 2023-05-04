package cart.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String authorization = webRequest.getHeader("authorization");

        if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
            throw new IllegalArgumentException();
        }
        final String base64Credentials = authorization.substring("Basic".length()).trim();
        final byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
        final String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
        final List<String> values = Arrays.asList(credentials.split(":", 2));

        final String email = values.get(0);
        final String password = values.get(1);

        return new Credentials(email, password);
    }
}
