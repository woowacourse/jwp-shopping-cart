package cart.common.argumentresolver;

import cart.common.auth.AuthHeaderExtractor;
import cart.controller.member.dto.MemberRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int PASSWORD_INDEX = 1;
    private static final int EMAIL_INDEX = 0;

    private final AuthHeaderExtractor authHeaderExtractor;

    public MemberArgumentResolver(AuthHeaderExtractor authHeaderExtractor) {
        this.authHeaderExtractor = authHeaderExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberRequest.class) && parameter.hasParameterAnnotation(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        List<String> authorizationHeaderValues = authHeaderExtractor.extract(webRequest.getHeader("Authorization"));

        String email = authorizationHeaderValues.get(EMAIL_INDEX);
        String password = authorizationHeaderValues.get(PASSWORD_INDEX);

        return new MemberRequest(email, password);
    }
}
