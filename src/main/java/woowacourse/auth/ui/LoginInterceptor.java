package woowacourse.auth.ui;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final List<RequestEndPoint> excludedEndPoint = List.of(
            new RequestEndPoint("POST", "/api/customers")
    );
    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {

        final String requestMethod = request.getMethod();
        final String requestURI = request.getRequestURI();
        final RequestEndPoint requestEndPoint = new RequestEndPoint(requestMethod, requestURI);

        if (excludedEndPoint.contains(requestEndPoint) || requestMethod.equals("OPTIONS")) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }

        request.setAttribute("token", token);
        return true;
    }

    public static class RequestEndPoint {
        private final String httpMethod;
        private final String uri;

        public RequestEndPoint(final String httpMethod, final String uri) {
            this.httpMethod = httpMethod;
            this.uri = uri;
        }

        public String getHttpMethod() {
            return httpMethod;
        }

        public String getUri() {
            return uri;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof RequestEndPoint)) {
                return false;
            }
            final RequestEndPoint that = (RequestEndPoint) o;
            return Objects.equals(httpMethod, that.httpMethod) && Objects.equals(uri, that.uri);
        }

        @Override
        public int hashCode() {
            return Objects.hash(httpMethod, uri);
        }
    }
}
