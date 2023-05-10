package cart.domain.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class WrongProductNameException extends RuntimeException implements CustomException {

    public WrongProductNameException() {
        super();
    }

    public enum Language {
        EN("Product name must be at least 1 letter and not more than 50 characters."),
        KO("상품명은 1글자 이상, 50글자 이하여야합니다."),
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
