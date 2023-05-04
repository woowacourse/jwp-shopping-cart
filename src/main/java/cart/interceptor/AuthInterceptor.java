package cart.interceptor;

import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String BASIC = "Basic";
    private static final int AUTHORIZATION_TYPE_INDEX = 0;
    private static final int CREDENTIAL_INDEX = 1;
    private static final String AUTHORIZATION_DELIMITER = " ";

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) throws Exception {
        final String authorization = request.getHeader("Authorization");
        final String[] authorizationInfo = authorization.split(AUTHORIZATION_DELIMITER);
        final String authorizationType = authorizationInfo[AUTHORIZATION_TYPE_INDEX];
        if (authorizationType.equals(BASIC)) {
            final String encodedCredential = authorizationInfo[CREDENTIAL_INDEX];
            final byte[] decodedByteArray = Base64.getDecoder().decode(encodedCredential);
            final String decodedCredential = new String(decodedByteArray);
            request.setAttribute("AuthInfo", decodedCredential);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
