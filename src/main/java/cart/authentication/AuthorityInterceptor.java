package cart.authentication;

import cart.dao.member.MemberDao;
import cart.entity.member.Member;
import cart.entity.member.Role;
import cart.exception.notfound.MemberNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public AuthorityInterceptor(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            throw new MemberNotFoundException();
        }

        if ((authorization.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = authorization.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[0];
            String password = credentials[1];
            Member member = memberDao.findByEmailAndPassword(email, password).orElseThrow(MemberNotFoundException::new);
            return checkAuthority(member);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean checkAuthority(Member member) {
        return member.getRole() == Role.ADMIN;
    }
}
