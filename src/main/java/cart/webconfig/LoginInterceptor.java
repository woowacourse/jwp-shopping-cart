package cart.webconfig;

import cart.dao.MemberDao;
import cart.infrastructure.AuthInfo;
import cart.entity.MemberEntity;
import cart.infrastructure.AuthorizationExtractor;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;
    private final AuthorizationExtractor authorizationExtractor;

    public LoginInterceptor(MemberDao memberDao, AuthorizationExtractor extractor) {
        this.memberDao = memberDao;
        this.authorizationExtractor = extractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfo authInfo = authorizationExtractor.extract(request);
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        Optional<MemberEntity> member = memberDao.findByEmailAndPassword(email, password);
        if (member.isPresent()) {
            request.setAttribute("memberId", member.get().getId());
            return true;
        }
        throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
    }
}
