package cart.service.exception;

import cart.controller.exceptionhandler.CustomException;
import java.util.Arrays;

public class DuplicateCartException extends RuntimeException implements CustomException {

    public DuplicateCartException() {
        super();
    }

    public enum Language {
        EN("This product has already been added."),
        KO("이미 추가된 상품입니다."),
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
