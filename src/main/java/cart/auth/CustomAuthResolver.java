package cart.auth;

import cart.dao.H2MemberDao;
import cart.entity.MemberEntity;
import cart.exception.AuthenticationException;
import cart.exception.InvalidTokenException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Objects;

@Component
public class CustomAuthResolver implements HandlerMethodArgumentResolver {
    private final H2MemberDao memberDao;

    public CustomAuthResolver(H2MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (nativeRequest == null) {
            return null;
        }
        String[] token = extractBasicToken(nativeRequest);

        validateToken(token);

        MemberEntity member = memberDao.findByEmail(token[0])
                .orElseThrow(() -> new AuthenticationException("잘못된 인증정보입니다."));

        validatePassword(token, member);

        return member;
    }

    private void validateToken(String[] token) {
        if (token.length != 2) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }

    private void validatePassword(String[] token, MemberEntity member) {
        if (!Objects.equals(token[1], member.getPassword())) {
            throw new AuthenticationException("잘못된 인증정보입니다.");
        }
    }

    private String[] extractBasicToken(HttpServletRequest nativeRequest) {
        String requestHeader = nativeRequest.getHeader("Authorization");
        String credential = StringUtils.delete(requestHeader, "Basic").strip();

        byte[] decode = Base64.getDecoder().decode(credential);
        String result = new String(decode);

        return result.split(":");
    }
}
