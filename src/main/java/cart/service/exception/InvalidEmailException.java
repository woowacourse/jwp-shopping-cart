package cart.service.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(Language language) {
        super(language.msg);
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

}
