package cart.auth.interceptor;

import cart.auth.extractor.BasicAuthorizationExtractor;
import cart.dto.request.CertifiedCustomer;
import cart.service.CustomerService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String CERTIFIED_CUSTOMER = "CertifiedCustomer";

    @Autowired
    private final CertifiedCustomer certifiedCustomer;
    private final CustomerService customerService;

    public AuthInterceptor(final CertifiedCustomer certifiedCustomer, final CustomerService customerService) {
        this.certifiedCustomer = certifiedCustomer;
        this.customerService = customerService;
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        setCertifiedCustomer(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void setCertifiedCustomer(final HttpServletRequest request) {
        final String authorizationContent = request.getHeader(HttpHeaders.AUTHORIZATION);
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor(decode(authorizationContent));
        final Long customerId = customerService.findCertifiedMemberIdByEmailAndPassword(
            extractor.extractUsername(),
            extractor.extractPassword()
        );
        certifiedCustomer.setId(customerId);
        certifiedCustomer.setEmail(extractor.extractUsername());
        certifiedCustomer.setPassword(extractor.extractPassword());
    }

    private String decode(final String authorizationContent) {
        return AuthorizationType.findType(authorizationContent)
            .decode(authorizationContent);
    }
}
