package cart.auth.interceptor;

import cart.auth.extractor.BasicAuthorizationExtractor;
import cart.dto.request.CertifiedCustomer;
import cart.service.CustomerService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String CERTIFIED_CUSTOMER = "CertifiedCustomer";

    private final CustomerService customerService;

    public AuthInterceptor(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        request.setAttribute(CERTIFIED_CUSTOMER, authByBasic(request));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private CertifiedCustomer authByBasic(final HttpServletRequest request) {
        final String authorizationContent = request.getHeader(HttpHeaders.AUTHORIZATION);
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor(decode(authorizationContent));
        final Long customerId = customerService.findCertifiedMemberIdByEmailAndPassword(
            extractor.extractUsername(),
            extractor.extractPassword()
        );

        return new CertifiedCustomer(customerId, extractor.extractUsername(), extractor.extractPassword());
    }

    private String decode(final String authorizationContent) {
        return AuthorizationType.findType(authorizationContent)
            .decode(authorizationContent);
    }
}
