package woowacourse.auth.ui;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dao.CustomerDao;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final CustomerDao customerDao;
    private final AuthService authService;

    public AuthenticationPrincipalArgumentResolver(final AuthService authService, final CustomerDao customerDao) {
        this.authService = authService;
        this.customerDao = customerDao;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final var httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        final var token = AuthorizationExtractor.extract(httpServletRequest);
        final var username = authService.decode(token);
        return customerDao.findCustomerByUserName(username);
    }
}
