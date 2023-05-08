package cart.infratstructure;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor {

    AuthInfo extract(HttpServletRequest request);
}
