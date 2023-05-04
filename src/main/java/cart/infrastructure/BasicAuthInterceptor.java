package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final BasicAuthProvider basicAuthProvider;

    public BasicAuthInterceptor(BasicAuthProvider basicAuthProvider) {
        this.basicAuthProvider = basicAuthProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = basicAuthProvider.resolveUser(request.getHeader(AUTHORIZATION_HEADER));
        request.setAttribute("user", user);
        return true;
    }
}
