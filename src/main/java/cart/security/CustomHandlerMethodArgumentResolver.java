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
    private static final String DELIMITER = ":";
    private static final Integer EMAIL_INDEX = 0;
    private static final Integer PASSWORD_INDEX = 1;

    private final MemberDao memberDao;

    public CustomHandlerMethodArgumentResolver(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticate.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        final String token = webRequest.getHeader(HEADER_PREFIX);
        final String basicToken = getBasicToken(token);
        final String[] authInformation = getAuthInformation(basicToken);
        final String email = authInformation[EMAIL_INDEX];
        final String password = authInformation[PASSWORD_INDEX];

        return getMemberId(email, password);
    }

    private String getBasicToken(final String token) {
        if (!StringUtils.hasText(token)) {
            throw HeaderPrefixNotFoundException.EXCEPTION;
        }
        if (!token.startsWith(BASIC_TYPE)) {
            throw TokenTypeNotMatchException.EXCEPTION;
        }
        return token.replaceFirst(BASIC_TYPE, "");
    }

    private Long getMemberId(String email, String password) {
        final MemberEntity member = memberDao.findByEmail(email)
                .orElseThrow(() -> NotFoundMemberException.EXCEPTION);

        if (!password.equals(member.getPassword())) {
            throw PasswordNotMatchException.EXCEPTION;
        }
        return member.getId();
    }

    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(DELIMITER);
    }

}
