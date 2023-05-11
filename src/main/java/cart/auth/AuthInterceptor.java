package cart.auth;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public AuthInterceptor(final BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) throws Exception {
        List<String> credentials = basicAuthorizationExtractor.extract(request);
        request.setAttribute("email", credentials.get(0));
        request.setAttribute("password", credentials.get(1));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
