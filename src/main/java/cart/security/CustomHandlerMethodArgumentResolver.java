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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    private static final String DELIMITER = ":";
    private static final Integer EMAIL_INDEX = 0;
    private static final Integer PASSWORD_INDEX = 1;

<<<<<<< HEAD
=======
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    private final MemberDao memberDao;

    public CustomHandlerMethodArgumentResolver(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
    // supportsParameter 메서드는 현재 파라미터를 resolver가 지원하는지에 대한 boolean을 리턴한다.
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticate.class)
                && parameter.getParameterType().equals(Long.class);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
    // resolveArgument 메서드는 실제로 바인딩을 할 객체를 리턴한다.
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
        final String token = webRequest.getHeader(HEADER_PREFIX);
        final String basicToken = getBasicToken(token);
        final String[] authInformation = getAuthInformation(basicToken);
        final String email = authInformation[EMAIL_INDEX];
        final String password = authInformation[PASSWORD_INDEX];
<<<<<<< HEAD

        return getMemberId(email, password);
    }

    private String getBasicToken(final String token) {
=======
        final String token = getBasicToken(webRequest);
        final String[] authInformation = getAuthInformation(token);
        final String email = authInformation[0];
        final MemberEntity member = memberDao.findByEmail(email)
                .orElseThrow(() -> NotFoundMemberException.EXCEPTION);
        final String password = authInformation[1];
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)

        return getMemberId(email, password);
    }

<<<<<<< HEAD
    private String getBasicToken(final NativeWebRequest webRequest) {
        final String token = webRequest.getHeader(HEADER_PREFIX);
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
    private String getBasicToken(final String token) {
>>>>>>> db0c1803 (feat: CartDao save 테스트)
        if (!StringUtils.hasText(token)) {
            throw HeaderPrefixNotFoundException.EXCEPTION;
        }
        if (!token.startsWith(BASIC_TYPE)) {
            throw TokenTypeNotMatchException.EXCEPTION;
        }
        return token.replaceFirst(BASIC_TYPE, "");
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    private Long getMemberId(String email, String password) {
        final MemberEntity member = memberDao.findByEmail(email)
                .orElseThrow(() -> NotFoundMemberException.EXCEPTION);

        if (!password.equals(member.getPassword())) {
            throw PasswordNotMatchException.EXCEPTION;
        }
        return member.getId();
    }

<<<<<<< HEAD
    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(DELIMITER);
=======
    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(":");
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
    private String[] getAuthInformation(final String token) {
        final String emailAndPassword = new String(Base64Utils.decodeFromString(token));
        return emailAndPassword.split(DELIMITER);
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    }

}
