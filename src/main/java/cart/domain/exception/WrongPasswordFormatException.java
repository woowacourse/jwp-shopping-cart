package cart.domain.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class WrongPasswordFormatException extends RuntimeException implements CustomException {

    public WrongPasswordFormatException() {
        super();
    }

    public enum Language {
        EN("Password must be between 8 and 20 characters."),
        KO("비밀번호는 8글자 ~ 20글자 사이여야 합니다."),
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
