package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor<T> {

    String Authorization = "Authorization";

    T extract(HttpServletRequest request);
}
