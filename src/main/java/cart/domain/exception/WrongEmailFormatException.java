package cart.domain.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class WrongEmailFormatException extends RuntimeException implements CustomException {

    public WrongEmailFormatException() {
        super();
    }

    public enum Language {
        EN("E-mail can consist of only numbers, lowercase letters, and _"),
        KO("이메일은 숫자, 알파벳 소문자, _로만 구성할 수 있습니다."),
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
