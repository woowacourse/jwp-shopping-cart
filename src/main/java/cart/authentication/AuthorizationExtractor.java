package cart.authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {
    String AUTHORIZATION = "authorization";

    T extract(HttpServletRequest request);

}
