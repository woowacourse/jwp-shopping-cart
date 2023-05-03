package cart.controller;

import cart.controller.dto.MemberRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.getParameterType().equals(MemberRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String credentials = httpServletRequest.getHeader("authorization")
            .substring("Basic ".length());
        String[] decoded = new String(Base64Utils.decode(credentials.getBytes())).split(":");
        if (decoded.length != 2) {
            throw new IllegalArgumentException("credential의 형식이 잘못되었습니다.");
        }
        return new MemberRequest(decoded[0], decoded[1]);
    }
}
