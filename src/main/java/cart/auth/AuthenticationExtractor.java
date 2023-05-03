package cart.auth;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    T extract(HttpServletRequest request);
}
