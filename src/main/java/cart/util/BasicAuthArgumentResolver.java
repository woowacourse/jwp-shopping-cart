package cart.util;

import cart.dao.member.MemberDao;
import cart.domain.member.Member;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final String NO_MEMBER_MESSAGE = "해당하는 유저가 없습니다.";
    private static final String EMPTY_HEADER_MESSAGE = "Authorization 헤더가 비어있습니다.";
    private static final String AUTH_TYPE_ERROR_MESSAGE = "인증 타입이 Basic이 아닙니다.";

    private final MemberDao memberDao;

    public BasicAuthArgumentResolver(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class) && parameter.hasParameterAnnotation(BasicAuth.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String[] credentials = extractHeader(webRequest);

        final Member member = memberDao.findByEmailAndPassword(credentials[0], credentials[1])
                .orElseThrow(() -> new AuthenticationException(NO_MEMBER_MESSAGE));
        return member.getId();
    }

    private String[] extractHeader(NativeWebRequest webRequest) {
        final String header = webRequest.getHeader(AUTHORIZATION_HEADER);
        validateHeader(header);

        final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        return new String(Base64.decodeBase64(authHeaderValue)).split(DELIMITER);
    }

    private void validateHeader(final String header) {
        if (header == null) {
            throw new AuthenticationException(EMPTY_HEADER_MESSAGE);
        }
        if (!(header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            throw new AuthenticationException(AUTH_TYPE_ERROR_MESSAGE);
        }
    }
}
