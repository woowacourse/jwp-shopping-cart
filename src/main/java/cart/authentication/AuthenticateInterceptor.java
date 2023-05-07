package cart.authentication;

import cart.dao.member.MemberDao;
import cart.exception.notfound.MemberNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticateInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public AuthenticateInterceptor(final MemberDao memberDao) {
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
            memberDao.findByEmail(email).orElseThrow(MemberNotFoundException::new);
            return true;
        }

        return false;
    }
}
