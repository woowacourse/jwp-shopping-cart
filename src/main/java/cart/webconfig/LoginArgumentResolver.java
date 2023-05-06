package cart.webconfig;

import cart.dao.MemberDao;
import cart.infrastructure.AuthInfo;
import cart.entity.MemberEntity;
import cart.infrastructure.AuthorizationExtractor;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberDao memberDao;
    private final AuthorizationExtractor authorizationExtractor;

    public LoginArgumentResolver(MemberDao memberDao, AuthorizationExtractor authorizationExtractor) {
        this.memberDao = memberDao;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        AuthInfo authInfo = authorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        Optional<MemberEntity> member = memberDao.findByEmailAndPassword(email, password);
        if (member.isPresent()) {
            return member.get().getId();
        }
        throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }
}
