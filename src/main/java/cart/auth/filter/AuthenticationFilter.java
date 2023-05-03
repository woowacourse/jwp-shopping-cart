package cart.auth.filter;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import cart.repository.user.UserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private final UserRepository userRepository;

    public AuthenticationFilter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String basicHeader = getBasicAuthHeader(request.getHeader(AUTHORIZATION));

        if (basicHeader == null) {
            response.sendError(SC_UNAUTHORIZED, "Authorization basicHeader not found");
            return;
        }

        final String decodedString = new String(Base64.decodeBase64(basicHeader));

        final String[] credentials = decodedString.split(DELIMITER);
        final String email = credentials[0];
        final String password = credentials[1];

        if (!userRepository.existsByEmailAndPassword(email, password)) {
            response.sendError(SC_UNAUTHORIZED, "Authorization basicHeader not found");
        }

        filterChain.doFilter(request, response);
    }

    private String getBasicAuthHeader(final String authHeader) {
        if (authHeader == null || !authHeader.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            return null;
        }
        return authHeader.substring(BASIC_TYPE.length()).trim();
    }
}
