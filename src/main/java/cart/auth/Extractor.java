package cart.auth;

import org.springframework.http.HttpHeaders;

import cart.dto.user.UserRequest;

public interface Extractor {
    UserRequest extractUser(HttpHeaders header);
}
