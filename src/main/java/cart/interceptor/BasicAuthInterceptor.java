package cart.interceptor;

import cart.entity.member.Member;
import cart.entity.member.MemberDao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {

    private static final String BASIC_TYPE_PREFIX = "Basic";
    private static final String DELIMITER = ":";

    private final MemberDao memberDao;

    public BasicAuthInterceptor(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");
        final boolean isBasicAuthentication = authorization != null && authorization.toLowerCase().startsWith(BASIC_TYPE_PREFIX.toLowerCase());

        if (!isBasicAuthentication) {
            final ResponseEntity<String> unauthorizedResponse = new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            response.getWriter().write(unauthorizedResponse.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String authHeaderValue = authorization.substring(BASIC_TYPE_PREFIX.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);

        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[0];
        String password = credentials[1];

        final Member authenticatedMember = memberDao.findByEmailAndPassword(email, password).orElse(null);

        if (authenticatedMember == null) {
            final ResponseEntity<String> unauthorizedResponse = new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            response.getWriter().write(unauthorizedResponse.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        request.setAttribute("authenticatedMember", authenticatedMember);
        return true;
    }
}


