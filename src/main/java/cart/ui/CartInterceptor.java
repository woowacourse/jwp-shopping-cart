package cart.ui;

import cart.auth.BasicAuthorizationExtractor;
import cart.entity.Member;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartInterceptor extends HandlerInterceptorAdapter {
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member member = basicAuthorizationExtractor.extract(request);

        if (member == null || member.getEmail() == null || member.getPassword() == null) {
            throw new AuthorizationException();
        }

        return super.preHandle(request, response, handler);
    }
}
