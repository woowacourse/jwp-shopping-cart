package cart.auth;

import cart.dao.member.MemeberDao;
import cart.domain.Member;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.NoSuchElementException;
import java.util.Optional;


public class BasicAuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final MemeberDao memeberDao;

    public BasicAuthArgumentResolver(MemeberDao memeberDao) {
        this.memeberDao = memeberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        String header = webRequest.getHeader(AUTHORIZATION);

        if (header == null) {
            return null;
        }

        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];

            Optional<Member> member = memeberDao.findByEmail(email);
            return getMember(password, member);
        }

        return null;
    }

    private Member getMember(String password, Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NoSuchElementException("회원이 존재하지 않습니다");
        }
        if (!member.get().isPasswordCorrect(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        return member.get();
    }

}