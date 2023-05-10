package cart.intercepter;

import cart.controller.dto.request.LoginRequest;
import cart.util.BasicAuthExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class BasicAuthorizationInterceptor implements HandlerInterceptor {

    private final BasicAuthExtractor basicAuthExtractor = new BasicAuthExtractor();

    public BasicAuthorizationInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginRequest loginRequest = basicAuthExtractor.extract(request);
        return Objects.nonNull(loginRequest);
    }
}
