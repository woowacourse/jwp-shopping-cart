package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.exception.ForbiddenException;
import woowacourse.auth.exception.UnauthorizedException;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerDao customerDao;

    public AdminAuthenticationInterceptor(JwtTokenProvider jwtTokenProvider,
                                          CustomerDao customerDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerDao = customerDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getMethod().equals("POST")) {
            validateIsAdmin(request);
        }
        return true;
    }

    private void validateIsAdmin(HttpServletRequest request) {
        String email = getPayload(request);
        Customer customer = customerDao.findByEmail(email).orElseThrow(UnauthorizedException::new);

        if (!customer.isAdmin()) {
            throw new ForbiddenException();
        }
    }

    private String getPayload(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);

        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }

        return jwtTokenProvider.getPayload(token);
    }
}
