package woowacourse.auth.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.shoppingcart.domain.customer.UserName;

public class UserNameResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(woowacourse.auth.support.UserNameResolver.class);
    }

    @Override
    public UserName resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final UserName userName = (UserName) request.getAttribute("userName");
        validateUserName(userName);
        return userName;
    }

    private void validateUserName(final UserName userName) {
        if (userName == null) {
            throw new AuthorizationException("알 수 없는 사용자입니다😅");
        }
    }
}
