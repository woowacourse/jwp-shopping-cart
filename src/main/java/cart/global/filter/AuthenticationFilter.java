package cart.global.filter;

import cart.global.exception.auth.CanNotFoundHeaderException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whiteList = {"/cart", "/carts"};

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String requestURI = request.getRequestURI();

        if (isNotHeaderCheckPath(requestURI)) {
            filterChain.doFilter(request, response);
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            validateHeader(header);

            filterChain.doFilter(request, response);
        } catch (CanNotFoundHeaderException exception) {
            writeErrorResponse(response, exception);
        }
    }

    private void writeErrorResponse(final HttpServletResponse response,
                                    final CanNotFoundHeaderException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        final PrintWriter printWriter = response.getWriter();
        printWriter.write(exception.getMessage());
        printWriter.flush();
    }

    private void validateHeader(final String header) {
        if (hasNotHeader(header)) {
            throw new CanNotFoundHeaderException();
        }
    }

    private boolean hasNotHeader(final String header) {
        return header == null;
    }

    private boolean isNotHeaderCheckPath(final String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
