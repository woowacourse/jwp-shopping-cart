package cart.service.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class InvalidEmailException extends RuntimeException implements CustomException {

    public InvalidEmailException() {
        super();
    }

    public enum Language {
        EN("This email is not available."),
        KO("사용할 수 없는 이메일입니다."),
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
                .filter(lan -> language.contains(lan.name()))
                .findFirst()
                .orElse(Language.KO);
        return matchingLanguage.msg;
    }

}
