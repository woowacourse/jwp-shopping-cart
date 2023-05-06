package cart.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(Language language) {
        super(language.msg);
    }

    public enum Language {
        EN("Login is required."),
        KO("로그인이 필요합니다."),
        NONE("");

        private final String msg;

        Language(String msg) {
            this.msg = msg;
        }
    }
}
