package cart.auth;

import cart.dao.H2MemberDao;
import cart.dao.MemberDao;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomAuthResolver implements HandlerMethodArgumentResolver {
    private static final int BASIC_TOKEN_SIZE = 2;
    private static final int BASIC_TOKEN_PASSWORD_INDEX = 1;

    private final MemberDao memberDao;

    public CustomAuthResolver(H2MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (nativeRequest == null) {
            return null;
        }
        List<String> token = extractBasicToken(nativeRequest);

        validateToken(token);

        MemberEntity member = memberDao.findByEmail(token.get(0))
                .orElseThrow(() -> new AuthenticationException("존재하지 않는 이메일 정보입니다." + System.lineSeparator() + "이메일 : " + token.get(0)));

        validatePassword(token, member);

        return member;
    }

    private void validateToken(List<String> token) {
        if (token.size() != BASIC_TOKEN_SIZE) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }

    private void validatePassword(List<String> token, MemberEntity member) {
        if (!Objects.equals(token.get(BASIC_TOKEN_PASSWORD_INDEX), member.getPassword())) {
            throw new AuthenticationException("잘못된 인증정보입니다.");
        }
    }

    private List<String> extractBasicToken(HttpServletRequest nativeRequest) {
        String requestHeader = nativeRequest.getHeader("Authorization");
        String credential = StringUtils.delete(requestHeader, "Basic").strip();

        byte[] decode = Base64.getDecoder().decode(credential);
        String result = new String(decode);

        return Arrays.stream(result.split(":"))
                .collect(Collectors.toList());
    }
}
