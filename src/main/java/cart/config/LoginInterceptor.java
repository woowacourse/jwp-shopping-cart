package cart.config;

import cart.auth.BasicAuthorizationExtractor;
import cart.controller.dto.MemberDto;
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
        MemberDto memberDto = extractor.extract(request.getHeader(AUTHORIZATION));
        validate(memberDto);

        return true;
    }

    private void validate(MemberDto memberDto) {
        MemberEntity findMember = memberDao.findByEmail(memberDto.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_ERROR_MESSAGE));

        if (findMember.hasDifferPassword(memberDto.getPassword())) {
            throw new MemberNotFoundException(PASSWORD_ERROR_MESSAGE);
        }
    }
}
