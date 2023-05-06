package cart.auth;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor {

    String AUTHORIZATION = "Authorization";

    AuthInfo extract(HttpServletRequest request);
}
