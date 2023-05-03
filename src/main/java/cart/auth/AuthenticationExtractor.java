package cart.auth;

import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    T extract(NativeWebRequest request);
}
