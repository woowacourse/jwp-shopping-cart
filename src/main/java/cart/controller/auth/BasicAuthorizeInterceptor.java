package cart.controller.auth;

import cart.domain.user.Member;
import cart.persistance.dao.MemberDao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthorizeInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public BasicAuthorizeInterceptor(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String[] info = decode(request);
        final String email = info[0];
        final String password = info[1];
        final Long id = authorize(email, password);

        request.setAttribute("auth-info", id);
        return true;
    }

    private String[] decode(final HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.toLowerCase().startsWith(BASIC)) {
            throw new AuthorizationException();
        }
        final String code = header.substring(BASIC.length()).strip();
        final byte[] decoded = Base64.decodeBase64(code);
        final String decodedString = new String(decoded);
        final String[] info = decodedString.split(DELIMITER);
        return info;
    }

    private Long authorize(final String email, final String password) {
        final Member member = memberDao.findByEmail(email);
        if (member == null) {
            throw new AuthorizationException();
        }
        if (!member.getPassword().equals(password)) {
            throw new AuthorizationException();
        }
        return member.getId();
    }
}
