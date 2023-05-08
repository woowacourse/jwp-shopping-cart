package cart.ui;

import cart.auth.AuthorizationExtractor;
import cart.entity.Member;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CartInterceptor implements HandlerInterceptor {
    private final AuthorizationExtractor<Member> authorizationExtractor;

    public CartInterceptor(AuthorizationExtractor authorizationExtractor) {
        this.authorizationExtractor = authorizationExtractor;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member member = authorizationExtractor.extract(request);

        if (member == null || member.getEmail() == null || member.getPassword() == null) {
            throw new AuthorizationException();
        }
        return true;
    }
}
