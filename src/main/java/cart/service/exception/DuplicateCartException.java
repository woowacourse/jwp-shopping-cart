package cart.service.exception;

public class DuplicateCartException extends RuntimeException {

    public DuplicateCartException(Language language) {
        super(language.msg);
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

}
