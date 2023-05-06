package cart.infrastructure;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor {
    AuthInfo extract(HttpServletRequest request);
}
