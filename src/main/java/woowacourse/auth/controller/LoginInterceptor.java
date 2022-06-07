package woowacourse.auth.controller;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.support.AuthorizationExtractor;

public class LoginInterceptor implements HandlerInterceptor {

    public static final List<RequestEndPoint> excludedEndPoint = List.of(
            new RequestEndPoint("POST", "/api/customers"),
            new RequestEndPoint("POST", "/api/auth/login")
    );

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        final String requestMethod = request.getMethod();
        if (requestMethod.equals("OPTIONS")) {
            return true;
        }

        final String requestURI = request.getRequestURI();
        final RequestEndPoint requestEndPoint = new RequestEndPoint(requestMethod, requestURI);

        if (excludedEndPoint.contains(requestEndPoint)) {
            return true;
        }

        final String token = AuthorizationExtractor.extract(request);
        request.setAttribute("token", token);
        return HandlerInterceptor.super.preHandle(request, response, handler);
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
