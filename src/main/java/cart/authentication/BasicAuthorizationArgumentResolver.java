package cart.authentication;

import cart.dto.member.MemberRequest;
import cart.exception.notfound.MemberNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class BasicAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private final AuthorizationExtractor authorizationExtractor;

    public BasicAuthorizationArgumentResolver(final AuthorizationExtractor authorizationExtractor) {
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
        final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Object extactedMember = authorizationExtractor.extact(httpServletRequest);
        if (!(extactedMember instanceof MemberInfo)) {
            throw new MemberNotFoundException();
        }

        MemberInfo memberInfo = (MemberInfo) extactedMember;

        return new MemberRequest(memberInfo.getEmail(), memberInfo.getPassword());
    }
}
