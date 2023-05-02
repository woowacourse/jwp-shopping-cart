package cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends RuntimeException {

    public final String realm;

    public AuthException(final String message, final String realm) {
        super(message);
        this.realm = realm;
    }

    public String getRealm() {
        return realm;
    }
}
