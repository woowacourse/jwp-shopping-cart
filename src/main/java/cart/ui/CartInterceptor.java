package cart.ui;

import cart.auth.AuthorizationExtractor;
import cart.auth.BasicAuthorizationExtractor;
import cart.entity.Member;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CartInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor<Member> basicAuthorizationExtractor;

    public CartInterceptor(BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member member = basicAuthorizationExtractor.extract(request);

        if (member == null || member.getEmail() == null || member.getPassword() == null) {
            throw new AuthorizationException();
        }
        return true;
    }
}
