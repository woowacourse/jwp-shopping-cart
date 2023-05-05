package cart.interceptor;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_DELIMITER = " ";
    private static final int AUTHORIZATION_TYPE_INDEX = 0;
    private static final String BASIC_TYPE = "Basic";
    private static final int CREDENTIAL_INDEX = 1;

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION_HEADER);
        final String[] authorizationInfo = authorization.split(AUTHORIZATION_DELIMITER);
        final String authorizationType = authorizationInfo[AUTHORIZATION_TYPE_INDEX];
        if (authorizationType.equals(BASIC_TYPE)) {
            final String encodedCredential = authorizationInfo[CREDENTIAL_INDEX];
            final byte[] decodedByteArray = Base64.getDecoder().decode(encodedCredential);
            request.setAttribute("AuthInfo", new String(decodedByteArray));
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
