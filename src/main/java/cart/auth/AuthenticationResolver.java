package cart.auth;

import cart.dao.MemberJdbcDao;
import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final AuthorizationParser<AuthInfo> authorizationParser;
    private final MemberJdbcDao memberDao;

    public AuthenticationResolver(
            final AuthorizationParser<AuthInfo> authorizationParser,
            final MemberJdbcDao memberDao
    ) {
        this.authorizationParser = authorizationParser;
        this.memberDao = memberDao;
    }

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
    ) {
        final AuthInfo authInfo = authorizationParser.parse(webRequest.getHeader(AUTHORIZATION));

        final Email givenEmail = new Email(authInfo.getEmail());
        final Password givenPassword = new Password(authInfo.getPassword());

        final Member savedMember = memberDao.findByEmail(givenEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버는 찾을 수 없습니다."));

        if (savedMember.isNotSamePassword(givenPassword)) {
            throw new IllegalArgumentException("멤버 정보가 다릅니다");
        }

        return savedMember;
    }
}
