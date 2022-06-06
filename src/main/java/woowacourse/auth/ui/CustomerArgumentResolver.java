package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.constant.RequestAttributes;
import woowacourse.auth.domain.token.Token;
import woowacourse.auth.domain.user.Customer;

public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public CustomerArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Customer.class);
    }

    @Override
    public Customer resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Token token = (Token) request.getAttribute(RequestAttributes.TOKEN);
        return authService.findCustomerByToken(token);
    }
}
