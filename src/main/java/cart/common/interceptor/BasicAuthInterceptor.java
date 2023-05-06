package cart.common.interceptor;

import cart.common.auth.AuthHeaderExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    private final AuthHeaderExtractor authHeaderExtractor;

    public BasicAuthInterceptor(AuthHeaderExtractor authHeaderExtractor) {
        this.authHeaderExtractor = authHeaderExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return authHeaderExtractor.authenticate(request.getHeader(AUTHORIZATION));
    }
}
