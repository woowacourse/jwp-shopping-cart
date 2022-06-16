package woowacourse.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.support.AuthorizationExtractor;
import woowacourse.shoppingcart.support.JwtTokenProvider;
import woowacourse.shoppingcart.support.Login;

public class LoginCustomerResolver implements HandlerMethodArgumentResolver {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginCustomerResolver(final CustomerService customerService, final JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Customer resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = AuthorizationExtractor.extract(request);
        final String email = jwtTokenProvider.getPayload(token);

        return customerService.getByEmail(email);
    }

    @Override
    public String toString() {
        return "LoginCustomerResolver{" +
                "customerService=" + customerService +
                ", jwtTokenProvider=" + jwtTokenProvider +
                '}';
    }
}
