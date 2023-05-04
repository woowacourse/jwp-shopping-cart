package cart.auth;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    T extract(final HttpServletRequest request);
}
