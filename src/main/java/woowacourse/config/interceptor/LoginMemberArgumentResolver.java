package woowacourse.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import woowacourse.auth.support.LoginMemberPrincipal;
import woowacourse.auth.ui.dto.LoginMember;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        try {
            return LoginMember.from((long) httpServletRequest.getAttribute("id"));
        } catch (Exception e) {
            throw new IllegalArgumentException("로그인 아이디.... 리졸빙하다 오류...");
        }
    }
}
