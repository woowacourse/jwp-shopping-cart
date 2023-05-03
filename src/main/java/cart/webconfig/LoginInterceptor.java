package cart.webconfig;

import cart.dao.MemberDao;
import cart.dto.AuthInfo;
import cart.entity.MemberEntity;
import cart.infrastructure.BasicAuthorizationExtractor;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;
    private final BasicAuthorizationExtractor authorizationExtractor;

    public LoginInterceptor(MemberDao memberDao) {
        this.memberDao = memberDao;
        this.authorizationExtractor = new BasicAuthorizationExtractor();
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
        return false;
    }
}
