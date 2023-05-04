package cart.util;

import cart.dto.MemberAuthDto;
import java.util.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String BASIC_REGEX = "^Basic [A-Za-z0-9+/]+=*$";
    private static final String AUTHORIZATION_DELIMITER = " ";
    private static final int AUTHORIZATION_VALUE_INDEX = 1;
    private static final String VALUE_DELIMITER = ":";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberAuthDto.class) &&
                parameter.hasParameterAnnotation(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String authorization = webRequest.getHeader(AUTHORIZATION_HEADER_NAME);
        validateAuthorization(authorization);
        final String encodedAuth = authorization.split(AUTHORIZATION_DELIMITER)[AUTHORIZATION_VALUE_INDEX];
        final String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        final String[] auth = decodedAuth.split(VALUE_DELIMITER);
        validateAuth(auth);
        return new MemberAuthDto(auth[0], auth[1]);
    }

    private void validateAuthorization(final String authorization) {
        if (authorization == null) {
            throw new IllegalArgumentException("인증 정보가 존재하지 않습니다.");
        }
        if (!authorization.matches(BASIC_REGEX)) {
            throw new IllegalArgumentException("유효하지 않은 인증 정보 형식입니다.");
        }
    }

    private void validateAuth(final String[] auth) {
        if (auth.length != 2) {
            throw new IllegalArgumentException("유효하지 않은 인증 정보입니다.");
        }
    }
}
