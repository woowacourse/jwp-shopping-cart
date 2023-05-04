package cart.auth;

import cart.auth.exception.AuthorizationException;
import cart.dto.request.MemberRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {
        final String header = webRequest.getHeader(AUTHORIZATION);

        if (header == null) {
            throw new AuthorizationException("인증 정보가 없습니다.");
        }

        try {
            return extractMember(header);
        } catch (final Exception e) {
            throw new AuthorizationException("사용자 정보를 찾을 수 없습니다.");
        }
    }

    private MemberRequest extractMember(final String header) {
        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        final byte[] decodeBytes = Base64.decodeBase64(authHeaderValue);
        final String decodedString = new String(decodeBytes);

        final String[] credentials = decodedString.split(DELIMITER);
        final String username = credentials[0];
        final String password = credentials[1];

        return new MemberRequest(username, password);
    }
}
