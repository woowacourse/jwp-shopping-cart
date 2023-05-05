package cart.infrastructure;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {
    private final BasicAuthProvider basicAuthProvider;

    public BasicAuthInterceptor(BasicAuthProvider basicAuthProvider) {
        this.basicAuthProvider = basicAuthProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = basicAuthProvider.resolveUser(request.getHeader(AUTHORIZATION));
        request.setAttribute("user", user);
        return true;
    }
}
