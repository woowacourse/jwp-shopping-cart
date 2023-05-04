package cart.auth;

import cart.config.BasicAuthorizationExtractor;
import cart.controller.dto.AuthInfo;
import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private final BasicAuthorizationExtractor extractor;
    private final MemberDao memberDao;

    public LoginInterceptor(BasicAuthorizationExtractor extractor, MemberDao memberDao) {
        this.extractor = extractor;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthInfo authInfo = extractor.extract(request);
        validate(authInfo);

        return super.preHandle(request, response, handler);
    }

    private void validate(AuthInfo authInfo) {
        MemberEntity findMember = memberDao.findByEmail(authInfo.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        if (findMember.hasDifferPassword(authInfo.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
