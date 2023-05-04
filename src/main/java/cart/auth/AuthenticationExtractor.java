package cart.auth;

import cart.domain.entity.MemberEntity;
import org.springframework.web.context.request.NativeWebRequest;

public interface AuthenticationExtractor<T> {
    String AUTHORIZATION = "Authorization";

    MemberEntity extract(NativeWebRequest request);
}
