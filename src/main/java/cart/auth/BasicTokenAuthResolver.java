package cart.auth;

import cart.dao.MemberDao;
import cart.entity.MemberEntity;
import cart.exception.AuthenticationException;
import cart.exception.InvalidTokenException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BasicTokenAuthResolver implements TokenAuthResolver {
    private static final int BASIC_TOKEN_SIZE = 2;
    private static final int BASIC_TOKEN_PASSWORD_INDEX = 1;
    private static final String BASIC_TOKEN_PREFIX = "Basic";

    private final MemberDao memberDao;
    private MemberEntity member = null;

    public BasicTokenAuthResolver(MemberDao memberDao) {
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
        validateToken(nativeRequest);
        return member.getId();
    }

    @Override
    public void validateToken(HttpServletRequest servletRequest) {
        List<String> token = extractToken(servletRequest);

        if (token.size() != BASIC_TOKEN_SIZE) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다." + System.lineSeparator() + "token : " + token);
        }

        member = memberDao.findByEmail(token.get(0))
                .orElseThrow(() -> new AuthenticationException("존재하지 않는 이메일 정보입니다." + System.lineSeparator() + "이메일 : " + token.get(0)));

        validatePassword(token, member);
    }

    private void validatePassword(List<String> token, MemberEntity member) {
        if (!Objects.equals(token.get(BASIC_TOKEN_PASSWORD_INDEX), member.getPassword())) {
            throw new AuthenticationException("올바르지 않은 비밀번호입니다." + System.lineSeparator() + "password : " + token.get(BASIC_TOKEN_PASSWORD_INDEX));
        }
    }

    @Override
    public List<String> extractToken(HttpServletRequest nativeRequest) {
        String requestHeader = nativeRequest.getHeader("Authorization");
        String credential = StringUtils.delete(requestHeader, BASIC_TOKEN_PREFIX).strip();

        byte[] decode = Base64.getDecoder().decode(credential);
        String result = new String(decode);

        return Arrays.stream(result.split(":"))
                .collect(Collectors.toList());
    }
}
