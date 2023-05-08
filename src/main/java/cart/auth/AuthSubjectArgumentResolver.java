package cart.auth;

import cart.auth.infrastructure.AuthorizationExtractor;
import cart.member.dto.MemberRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthSubjectArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthorizationExtractor<MemberRequest> authorizationExtractor;
    
    public AuthSubjectArgumentResolver(final AuthorizationExtractor<MemberRequest> authorizationExtractor) {
        this.authorizationExtractor = authorizationExtractor;
    }
    
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthSubject.class)
                || parameter.getParameterType().equals(MemberRequest.class);
    }
    
    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final String header = webRequest.getHeader(AUTHORIZATION);
        validateHeader(header);
        
        return authorizationExtractor.extract(header);
    }
    
    private void validateHeader(final String header) {
        if (Objects.isNull(header) || !StringUtils.hasText(header)) {
            throw new IllegalArgumentException("[ERROR] 헤더가 존재하지 않습니다.");
        }
    }
}
