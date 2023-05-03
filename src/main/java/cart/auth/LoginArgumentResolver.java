package cart.auth;

import cart.domain.member.Member;
import cart.exception.auth.UnauthenticatedException;
import cart.service.MemberService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final MemberService memberService;

    public LoginArgumentResolver(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class) && parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTHORIZATION);
        validateHeader(header);
        String[] credentials = extractCredentials(header);

        validateCredentials(credentials);

        String email = credentials[0];
        String password = credentials[1];
        return memberService.login(email, password);
    }

    private void validateHeader(String header) {
        if (header == null || !header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new UnauthenticatedException();
        }
    }

    private String[] extractCredentials(String header) {
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        String decodedString = new String(Base64.decodeBase64(authHeaderValue));
        String[] credentials = decodedString.split(DELIMITER);
        validateCredentials(credentials);
        return credentials;
    }

    private void validateCredentials(String[] credentials) {
        if (credentials.length != 2) {
            throw new UnauthenticatedException();
        }
    }
}
