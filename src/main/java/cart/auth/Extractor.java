package cart.auth;

import org.springframework.http.HttpHeaders;

import cart.dto.UserRequest;

public interface Extractor {
    UserRequest extractUser(HttpHeaders header);
}
