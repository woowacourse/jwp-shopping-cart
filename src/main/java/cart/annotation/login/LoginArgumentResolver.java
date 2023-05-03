package cart.annotation.login;

import cart.dao.member.MemberDao;
import cart.entity.MemberEntity;
import org.springframework.core.MethodParameter;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_TYPE = "Basic ";
    public static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public LoginArgumentResolver(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasStringType = MemberEntity.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasStringType;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader(HEADER_NAME);
        HttpSession session = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class), "요청이 올바르지 않습니다. 다시 시도해주세요.").getSession();

        MemberEntity sessionMember = (MemberEntity) session.getAttribute(authorization);

        if (sessionMember == null) {
            MemberEntity member = Base64ToMemberEntity(authorization);
            session.setAttribute(authorization, member);

            return member;
        }

        return sessionMember;
    }

    private MemberEntity Base64ToMemberEntity(final String authorization) {
        if (authorization != null && authorization.startsWith(AUTHORIZATION_TYPE)) {
            String base64Credentials = authorization.substring(AUTHORIZATION_TYPE.length());
            String[] loginInfo = new String(Base64Utils.decodeFromString(base64Credentials)).split(DELIMITER);
            String memberEmail = loginInfo[0];
            String memberPassword = loginInfo[1];

            Optional<MemberEntity> member = memberDao.findByEmail(memberEmail);

            if (member.isEmpty()) {
                throw new LoginException("아이디가 일치하지 않습니다. 다시 시도해주세요.");
            }

            MemberEntity retrievedMember = member.get();

            if (retrievedMember.isSamePassword(memberPassword)) {
                return retrievedMember;
            }
            throw new LoginException("패스워드가 일치하지 않습니다. 다시 시도해주세요.");
        }

        throw new LoginException("입력하신 정보로 확인되는 사용자가 없습니다. 사용자를 선택해주세요.");
    }
}
