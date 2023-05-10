package cart.domain.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class WrongPriceException extends RuntimeException implements CustomException {

    public WrongPriceException() {
        super();
    }

    public enum Language {
        EN("Price must be greater than zero"),
        KO("가격은 0보다 커야합니다."),
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
