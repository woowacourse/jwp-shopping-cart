package cart;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {

        String header = request.getHeader("Authorization");
        if (header == null) {
            throw new IllegalArgumentException();
        }

        String[] authorization = header.split(" ");
        byte[] bytes = Base64.decodeBase64(authorization[1]);
        String auth = new String(bytes);
        String email = auth.split(":")[0];
        request.setAttribute("email", email);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
