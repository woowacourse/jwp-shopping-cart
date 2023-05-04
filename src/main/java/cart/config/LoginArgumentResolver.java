package cart.config;

import cart.annotation.Login;
import cart.dao.MemberDao;
import cart.domain.Member;
import cart.exception.custom.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    private final MemberDao memberDao;

    public LoginArgumentResolver(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class) &&
                parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(AUTHORIZATION_HEADER);

        if (authorization == null) {
            throw new UnauthorizedException("Authorization Header가 존재하지 않습니다.");
        }

        if (!authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new UnauthorizedException("Authorization Header는 'BASIC ****'과 같은 값으로 전달되어야 합니다.");
        }

        String value = authorization.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
        String[] authInfo = new String(Base64Utils.decodeFromString(value)).split(":");

        String email = authInfo[0];
        String password = authInfo[1];
        Member member = memberDao.findByEmail(email);
        member.validatePassword(password);

        return member;
    }
}
