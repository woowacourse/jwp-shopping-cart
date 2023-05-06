package cart.domain.exception;

public class WrongProductNameException extends RuntimeException {

    public WrongProductNameException(Language language) {
        super(language.msg);
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

}
