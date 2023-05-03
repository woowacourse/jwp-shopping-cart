package cart.controller.auth;

import cart.persistance.dao.MemberDao;
import cart.persistance.entity.user.Member;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class BasicAuthenticationsArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public BasicAuthenticationsArgumentResolver(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(BasicAuthentication.class)
                && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String[] info = decode(webRequest);
        final String email = info[0];
        final String password = info[1];

        return authorization(email, password);
    }

    private String[] decode(final NativeWebRequest webRequest) {
        final String header = webRequest.getHeader(AUTHORIZATION);
        if (header == null || !header.toLowerCase().startsWith(BASIC)) {
            throw new AuthorizationException();
        }
        final String code = header.substring(BASIC.length()).strip();
        final byte[] decoded = Base64.decodeBase64(code);
        final String decodedString = new String(decoded);
        final String[] info = decodedString.split(DELIMITER);
        return info;
    }

    private Member authorization(final String email, final String password) {
        final Member member = memberDao.findByEmail(email);
        if (member == null) {
            throw new AuthorizationException();
        }
        if (member.getPassword().equals(password)) {
            return member;
        }

        throw new AuthorizationException();
    }
}
