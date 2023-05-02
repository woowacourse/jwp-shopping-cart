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
        String credentials = extracted(authorization);
        byte[] decodedBytes = Base64.getDecoder().decode(credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] emailAndName = decodedString.split(":");
        String email = emailAndName[0];
        String password = emailAndName[1];
        Member member = memberDao.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
        if (!member.matchingPassword(password)) {
            throw new IllegalArgumentException();
        }
        return member;
    }

    private String extracted(String authorization) {
        if (authorization.isBlank()) {
//             TODO: 2023/05/01 커스텀 예외
            throw new IllegalArgumentException();
        }
        String regex = "^Basic [A-Za-z0-9+/]+=*$";
        if (!authorization.matches(regex)) {
            throw new IllegalArgumentException();
        }
        return authorization.replace("Basic ", "");
    }
}
