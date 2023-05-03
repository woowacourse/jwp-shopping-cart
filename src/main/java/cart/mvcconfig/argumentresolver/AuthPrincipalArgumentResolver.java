package cart.mvcconfig.argumentresolver;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import cart.dto.MemberAuthRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String BASIC_PREFIX = "Basic ";
    private static final String MEMBER_INFO_DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authValue = webRequest.getHeader(AUTH_HEADER_KEY);
        String encodedMemberInfo = authValue.replace(BASIC_PREFIX, "");
        String decodedMemberInfo =
                new String(Base64.getDecoder().decode(encodedMemberInfo), StandardCharsets.UTF_8);

        List<String> memberInfo = List.of(decodedMemberInfo.split(MEMBER_INFO_DELIMITER));
        String email = memberInfo.get(0);
        String password = memberInfo.get(1);
        return new MemberAuthRequest(email, password);
    }
}
