package cart.auth;

import cart.dao.MemberDaoImpl;
import cart.entity.MemberEntity;
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
    private final MemberDaoImpl memberDao;

    public CustomAuthResolver(MemberDaoImpl memberDao) {
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

        if (token.length != 2) {
            // TODO : 인증 오류에 대한 CustomException 추가하기.
            throw new IllegalArgumentException("인증 오류입니다");
        }
        // TODO : API Exception 추가하기
        MemberEntity member = memberDao.findByEmail(token[0])
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!Objects.equals(token[1], member.getPassword())) {
            // TODO : API Exception 추가하기
            throw new IllegalArgumentException("비밀번호가 틀렸습니다!!");
        }

        return member;
    }

    private static String[] extractBasicToken(HttpServletRequest nativeRequest) {
        String requestHeader = nativeRequest.getHeader("Authorization");
        String credential = StringUtils.delete(requestHeader, "Basic").strip();

        byte[] decode = Base64.getDecoder().decode(credential);
        String result = new String(decode);

        return result.split(":");
    }
}
