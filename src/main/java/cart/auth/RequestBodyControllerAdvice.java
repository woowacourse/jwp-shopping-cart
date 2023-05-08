package cart.auth;

import cart.domain.member.dto.MemberCreateRequest;
import java.lang.reflect.Type;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

@ControllerAdvice
public class RequestBodyControllerAdvice implements RequestBodyAdvice {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Override
    public boolean supports(final MethodParameter methodParameter, final Type targetType,
        final Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(EncodePassword.class)
            && targetType.equals(MemberCreateRequest.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage,
        final MethodParameter parameter,
        final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(final Object body, final HttpInputMessage inputMessage,
        final MethodParameter parameter, final Type targetType,
        final Class<? extends HttpMessageConverter<?>> converterType) {
        final MemberCreateRequest request = (MemberCreateRequest) body;
        final String encodePassword = passwordEncoder.encode(request.getPassword());
        return new MemberCreateRequest(request.getEmail(), encodePassword);
    }

    @Override
    public Object handleEmptyBody(final Object body, final HttpInputMessage inputMessage,
        final MethodParameter parameter, final Type targetType,
        final Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
