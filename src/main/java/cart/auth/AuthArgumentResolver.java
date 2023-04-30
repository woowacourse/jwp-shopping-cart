package cart.auth;

import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// TODO 테스트 하는법을 모르겠습니다..ㅠㅠㅠ 모킹밖에는 방법이 없을까요?
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_TYPE = "Basic ";
    private final MemberDao memberDao;

    public AuthArgumentResolver(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class)
                && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final String token = getBasicToken(webRequest);
        final String[] authInformation = getAuthInformation(token);
        final String email = authInformation[0];
        final Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 회원이 없습니다"));
        final String password = authInformation[1];
        member.login(password);
        return member;
    }

    private String getBasicToken(final NativeWebRequest webRequest) {
        final String token = webRequest.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException("Authorization 헤더가 없습니다.");
        }
        if (!token.startsWith(BASIC_TYPE)) {
            throw new AuthenticationException("토큰의 형식은 Basic 이어야 합니다.");
        }
        return token.replaceFirst(BASIC_TYPE, "");
    }

    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(":");
    }
}
