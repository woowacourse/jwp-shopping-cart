package cart.domain.exception;

public class WrongPriceException extends RuntimeException {

    public WrongPriceException(Language language) {
        super(language.msg);
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

}
