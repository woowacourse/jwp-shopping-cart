package cart.infratstructure;

import cart.dto.AuthInfo;
import javax.servlet.http.HttpServletRequest;

public interface AuthorizationExtractor {

    AuthInfo extract(HttpServletRequest request);
}
