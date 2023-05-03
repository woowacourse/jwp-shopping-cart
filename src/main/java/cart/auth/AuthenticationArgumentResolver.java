package cart.auth;

import cart.dao.MemberDao;
import cart.entity.Member;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberDao memberDao;

    public AuthenticationArgumentResolver(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.withContainingClass(Member.class)
                .hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        validateCredentials(authorization);
        String[] emailAndName = extractBasicAuthInfo(authorization);
        return getMember(emailAndName);
    }

    private void validateCredentials(String authorization) {
        validateNull(authorization);
        validateBasicAuth(authorization);
    }

    private void validateNull(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new AuthenticationException();
        }
    }

    private void validateBasicAuth(String authorization) {
        String regex = "^Basic [A-Za-z0-9+/]+=*$";

        if (!authorization.matches(regex)) {
            throw new AuthenticationException();
        }
    }

    private String[] extractBasicAuthInfo(String authorization) {
        String credentials = authorization.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedString.split(":");
    }

    private Member getMember(String[] emailAndName) {
        String email = emailAndName[0];
        String password = emailAndName[1];
        Member member = memberDao.findByEmail(email)
                .orElseThrow(AuthenticationException::new);
        if (!member.matchingPassword(password)) {
            throw new AuthenticationException();
        }
        return member;
    }
}
