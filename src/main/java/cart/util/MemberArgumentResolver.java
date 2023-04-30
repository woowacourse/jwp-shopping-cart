package cart.util;

import cart.dto.MemberAuthDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC = "Basic ";
    private static final int VALUE_INDEX = 1;
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberAuthDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String basic = webRequest.getHeader("Authorization");
        validateBasic(basic);
        final String encodedAuth = basic.split(" ")[VALUE_INDEX];
        final String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        final String[] auth = decodedAuth.split(DELIMITER);
        if (auth.length != 2) {
            throw new IllegalArgumentException("올바르지 않은 인증 정보입니다.");
        }
        return new MemberAuthDto(auth[0], auth[1]);
    }

    private void validateBasic(final String basic) {
        if (basic == null) {
            throw new IllegalArgumentException("인증 정보가 존재하지 않습니다.");
        }
        if (!basic.startsWith(BASIC)) {
            throw new IllegalArgumentException("올바르지 않은 인증 형식입니다.");
        }
    }
}
