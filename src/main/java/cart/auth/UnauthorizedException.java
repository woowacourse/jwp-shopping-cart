package cart.auth;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException implements CustomException {

    public UnauthorizedException() {
        super();
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

    @Override
    public String findMessageByLanguage(String language) {
        if (language == null) {
            return Language.KO.msg;
        }
        Language matchingLanguage = Arrays.stream(Language.values())
                .filter(lan -> language.toUpperCase().contains(lan.name()))
                .findFirst()
                .orElse(Language.KO);
        return matchingLanguage.msg;
    }
}
