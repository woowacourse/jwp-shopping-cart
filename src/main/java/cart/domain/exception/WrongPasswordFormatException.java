package cart.domain.exception;

public class WrongPasswordFormatException extends RuntimeException {

    public WrongPasswordFormatException(Language language) {
        super(language.msg);
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
}
