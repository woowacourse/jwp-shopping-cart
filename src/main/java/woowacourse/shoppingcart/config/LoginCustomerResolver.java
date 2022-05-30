package woowacourse.shoppingcart.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.exception.UnauthorizedTokenException;

@Component
public class LoginCustomerResolver implements HandlerMethodArgumentResolver {

    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginCustomerResolver(CustomerService customerService, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = AuthorizationExtractor.extract(request);

        boolean isValidToken = jwtTokenProvider.validateToken(token);
        if (!isValidToken) {
            throw new UnauthorizedTokenException();
        }

        String email = jwtTokenProvider.getPayload(token);

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
