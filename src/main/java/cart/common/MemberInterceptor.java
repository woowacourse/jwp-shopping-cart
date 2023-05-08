package cart.common;

import cart.dao.MemberDao;
import cart.domain.Member;
import cart.dto.entity.MemberEntity;
import cart.exception.UnAuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class MemberInterceptor implements HandlerInterceptor {

    private final MemberDao memberDao;

    public MemberInterceptor(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("Authorization");
        Member memberAuth = MemberExtractor.extract(header);
        Optional<MemberEntity> findMember = memberDao.findByEmail(new MemberEntity(memberAuth.getEmail(), memberAuth.getPassword()));
        findMember.orElseThrow(() -> new UnAuthorizationException());

        return true;
    }
}
