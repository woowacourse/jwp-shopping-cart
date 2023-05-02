package cart.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import cart.controller.argumentresolver.AuthDecoder;

public class LoginInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    private final AuthDecoder authDecoder;
    private final String authType;

    public LoginInterceptor(AuthDecoder authDecoder, String authType) {
        this.authDecoder = authDecoder;
        this.authType = authType;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String authorization = request.getHeader(AUTHORIZATION);
        request.setAttribute(authType, authDecoder.getEncoded(authorization));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
