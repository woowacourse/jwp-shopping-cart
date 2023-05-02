package cart.global.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whiteList = {"/cart", "/carts"};

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (hasHeaderCheckPath(requestURI)) {

            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (header == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasHeaderCheckPath(final String requestURI) {
        return PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
