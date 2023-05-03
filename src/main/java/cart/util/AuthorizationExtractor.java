package cart.util;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    T extract(final HttpServletRequest request);
}
