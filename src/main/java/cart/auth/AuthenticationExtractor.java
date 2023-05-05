package cart.auth;

import cart.domain.entity.Member;
import org.springframework.web.context.request.NativeWebRequest;

public interface AuthenticationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    Member extract(NativeWebRequest request);
}
