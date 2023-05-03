package cart.security;

import cart.annotation.Authenticate;
import cart.dao.MemberDao;
import cart.entity.MemberEntity;
import cart.exception.NotFoundMemberException;
import cart.security.exception.HeaderPrefixNotFoundException;
import cart.security.exception.PasswordNotMatchException;
import cart.security.exception.TokenTypeNotMatchException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_PREFIX = "Authorization";
    private static final String BASIC_TYPE = "Basic ";
    private final MemberDao memberDao;

    public CustomHandlerMethodArgumentResolver(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    // supportsParameter 메서드는 현재 파라미터를 resolver가 지원하는지에 대한 boolean을 리턴한다.
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticate.class)
                && parameter.getParameterType().equals(Long.class);
    }

    // resolveArgument 메서드는 실제로 바인딩을 할 객체를 리턴한다.
    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String token = getBasicToken(webRequest);
        final String[] authInformation = getAuthInformation(token);
        final String email = authInformation[0];
        final MemberEntity member = memberDao.findByEmail(email)
                .orElseThrow(() -> NotFoundMemberException.EXCEPTION);
        final String password = authInformation[1];

        if (!password.equals(member.getPassword())) {
            throw PasswordNotMatchException.EXCEPTION;
        }

        return member.getId();
    }

    private String getBasicToken(final NativeWebRequest webRequest) {
        final String token = webRequest.getHeader(HEADER_PREFIX);
        if (!StringUtils.hasText(token)) {
            throw HeaderPrefixNotFoundException.EXCEPTION;
        }
        if (!token.startsWith(BASIC_TYPE)) {
            throw TokenTypeNotMatchException.EXCEPTION;
        }
        return token.replaceFirst(BASIC_TYPE, "");
    }

    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(":");
    }

}
