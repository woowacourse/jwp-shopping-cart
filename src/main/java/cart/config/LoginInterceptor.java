package cart.config;

import cart.auth.BasicAuthorizationExtractor;
import cart.controller.dto.AuthInfo;
import cart.dao.MemberDao;
import cart.dao.entity.MemberEntity;
import cart.exception.MemberNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String MEMBER_ERROR_MESSAGE = "일치하는 회원이 없습니다.";
    private static final String PASSWORD_ERROR_MESSAGE = "비밀번호가 일치하지 않습니다.";

    private final BasicAuthorizationExtractor extractor;
    private final MemberDao memberDao;

    public LoginInterceptor(BasicAuthorizationExtractor extractor, MemberDao memberDao) {
        this.extractor = extractor;
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        AuthInfo authInfo = extractor.extract(request.getHeader(AUTHORIZATION));
        validate(authInfo);

        return true;
    }

    private void validate(AuthInfo authInfo) {
        MemberEntity findMember = memberDao.findByEmail(authInfo.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ERROR_MESSAGE));

        if (findMember.hasDifferPassword(authInfo.getPassword())) {
            throw new MemberNotFoundException(PASSWORD_ERROR_MESSAGE);
        }
    }
}
